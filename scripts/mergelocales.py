'''
Created on 2010-02-25

This module contains a script for merging java properties 
files for GWT internationalisation with UIBinder. For more
information on the motivation for this script, see:
  http://code.google.com/p/google-web-toolkit/issues/detail?id=4355
  
The basic use-case:
  - You use internationalisation markup directly in your UIBinder xml files
  - You may or may not use Constants or Messages resources within your java file
  - You want to keep all your translations for a language in the same file
  
The required setup:
  - You need the file src/com/google/gwt/i18n/client/LocalizableResource.properties
    This file will contain all the default locale translations. It must be
    named exactly this (see link above for details).
  - In the same directory you need LocalizableResource_xxxx.properties for each
    locale you want to support. For example, xxxx should be fr if you want to
    translate to french.
  - You need to GWT-compile your project with the "-extra" flag to generate extra
    files, including UIBinder translations. For example: "-extra extras"
  - If you use Constant or Message to define translations that are used within your
    java code but not within UIBinder, then you should add a non-UIBinder section 
    to your LocalizableResource file. This sections should begin with:
    ### NON-UIBINDER TRANSLATIONS
    And should contain all your translations  
  
Invoke in this way:
   mergelocales Extras_dir LocalizableResource_dir
   
For example:
   mergelocales ./extras/myproject ./src/com/google/gwt/i18n/client/

The .properties files in LocalizableResource_dir will be overwritten.
Although the new files should contain all the translations that were
on the original, be on the safe side and backup your files before
invoking this script. Needless to say, this script comes with no 
warranty whatsoever, but please feel free to contact me if you have
any questions, if you found bugs, or simply if you found the script 
useful.

@author: Philippe Beaudoin  (philippe.beaudoin@gmail.com)
'''

import os, re

import sys
import getopt

def main():
    # parse command line options
    try:
        opts, args = getopt.getopt(sys.argv[1:], "h", ["help"])
        if len(args) != 2:
            raise getopt.error( "Needs exactly two arguments." )            
    except getopt.error, msg:
        print msg
        print "for help use --help"
        sys.exit(2)
    # process options
    for o in opts:
        if o in ("-h", "--help"):
            print __doc__
            sys.exit(0)
    # process arguments
    mergeLocales( args[0], args[1] )


class InvalidProperty(Exception):
    '''
    Exception raised when a property cannot be properly parsed from a text file.
    '''
    def __init__(self, value):
        self.value = value
        
    def __str__(self):
        return repr(self.value)

class Property(object):
    '''
    A property is a single element of translation. 
    '''
    
    def __init__(self, nonUIBinder):
        '''
        Initialises a new property.
        '''
        self._comments = None
        self._key = None
        self._value = None
        self._nonUIBinder = nonUIBinder
        
    def getFromFile(self, file):
        '''
        Get the next property from a file. Throws a InvalidProperty exception
        if the property is not correctly formatted. This method will replace the
        comments, key and value of this Property object.
        
        @param file: The opened file object to read from.
        @return: True on success, False on EOF. 
        '''
        
        # Skip blank lines. Return false if EOF is reached
        line = readNonBlankLine(file)
        if not line:
            return False

        # Read the comment block
        self._comments = ''
        while line.startswith('#'):
            self._comments += line
            line = file.readline()

        # Read the key/value
        index = line.find('=')
        if index < 0 :
            raise InvalidProperty( 'No key=value found. File: %s.\nComment block: %s' % ( file.name, self._comments ) )        
        
        self._key = line[:index].strip()
        if len(self._key) == 0:
            raise InvalidProperty( 'Invalid key found. File: %s.\nLine: %sComment block: %s' % ( file.name, line, self._comments ) )
            
        self._value = line[index+1:]
        while self._value.endswith('\\\n'):
            self._value += file.readline()
        
        return True
    
    def setDeprecated(self):
        '''
        Ensures that this property is marked as deprecated, by including an appropriate comment.
        '''
        deprecatedComment = '# DEPRECATED\n'

        if not self._comments.find( deprecatedComment ):
            self._comments += deprecatedComment
        
    def __str__(self):
        comments = self._comments
        if self._nonUIBinder :
            comments += '# Non-UIBinder\n'
        return comments+self._key+'='+self._value+'\n' 

class PropertyCollection(object):
    '''
    A collection of Property object that can be merged or written to files 
    '''
    
    def __init__(self, comment):
        '''
        Initializes a new collection of Property objects.

        @param comment: The leading comment of this property object. (A string, each line should start with #)
        '''
        self._comment = ''
        self._properties = {}        
    
    def add(self, property):
        '''
        Adds an object of type Property to the collection
        
        @param property: The Property object to add
        '''
        self._properties[ property._key ] = property 
    
    def mergeWith(self, otherCollection, markDeprecated):
        '''
        Merge this collection with another one. Any key that is found in the other collection but not
        in this one will be added. If the markDeprecated parameter is true, any key that is found in 
        this collection but not in the other one will be marked as deprecated.  
        
        @param otherCollection: The collection to merge into this one.
        @param markDeprecated: A boolean. True if deprecated translations should be indicated, false otherwise.
        '''
        
        # Bring properties over
        for (key, property) in otherCollection._properties.iteritems():
            if not self._properties.has_key(key):
                self._properties[key] = property

        if not markDeprecated:
            return
        
        # Mark deprecated properties
        for (key, property) in self._properties.iteritems():
            if not otherCollection._properties.has_key(key):
                property.setDeprecated()

    def __str__(self):
        result = self._comment
        if len(result) > 0:
            result += '\n'        
        for property in self._properties.values():        
            result += str(property)
        return result
        
def readNonBlankLine(file):
    '''
    Eats all the blank lines from a file. A line is blank if it only contains spaces
    followed by a new line.
     
    @param file: The opened file to eat blanks from.
    @return: The first non-blank line read. An empty string if EOF is reached.
    '''
    
    line = '\n'
    while re.match( r'\s*\n', line, re.L ):
        line = file.readline()
    return line

def readComments(file):
    '''
    Read a comment block from a file.
    
    @param file: The opened file to read from
    @return: The comment block read (a string).
    '''
    
    comments = ''
    pos = file.tell()
    line = readNonBlankLine(file)
    while line.startswith('#'):
        comments += line
        pos = file.tell()
        line = file.readline()
    file.seek(pos)
    return comments


def enumeratePropertyFiles( path ):
    '''
    Looks in the specified directory for all property files, that is, files ending in .properties.
     
    @param path: The directory to look in.
    @return: A list of files.
    '''

    return filter( lambda filename: filename.endswith('.properties'),
                   os.listdir(path) ) 

def readPropertiesFromFile( filename ):
    '''
    Read all the properties from a given file.
    
    @param filename: The name of the file to read properties from.
    @return: A property collection
    '''
    
    file = open(filename, 'r')
    
    try:
        leadingComments = readComments(file)
        
        properties = PropertyCollection( leadingComments )
        nonUIBinder = False
        while(True):
            comments = readComments(file)
            if comments.startswith( '###' ):
                # Entering the non-UIBinder section
                nonUIBinder = True
            property = Property(nonUIBinder)
            result = property.getFromFile( file )
            if not result:
                break
            properties.add(property)
    finally:
        file.close()
    
    return properties
    
def findLocale( filename ):
    '''
    Identifies the locale given the filename of a property file.
    For example file_fr.properties will return fr.
    If there is no locale in the filename, returns the empty string.
    
    @param filename: The filename from which to extract the locale.
    @return: The locale or the empty string for the default locale.
    '''
    locale = ''  # Default locale when none is found
    match = re.match( r'[^_]*_([^\.]*)\.properties', filename )
    if match:
        locale = match.group(1)
    return locale
         
    
def mergeLocales( extrasDir, resourcesDir ):
    '''
    The main process of this script.
    
    Takes every property file in extrasDir and merge them to the 
    default locale file in resourcesDir. Then it merges this resource file with every other
    non-default local file in the directory.
    '''
    extraFiles = enumeratePropertyFiles( extrasDir )
    incomingProperties = PropertyCollection( '# Generated by mergelocales script\n# for default locale' )
    for filename in extraFiles:
        locale = findLocale( filename )
        if locale != '':
            print( "Skipping non-default locale in extra directory: " + filename )
            continue
        newProperties = readPropertiesFromFile( os.path.join( extrasDir, filename ), True )
        incomingProperties.mergeWith( newProperties, False )
        
    resourceFiles = enumeratePropertyFiles( resourcesDir )
    defaultLocaleFilename = None    
    for filename in resourceFiles:
        locale = findLocale( filename )
        if locale == '':
            if defaultLocaleFilename is not None :
                print( "Found multiple default locale resources. Using: %s  (Disregarding: %s)" % (defaultLocaleFilename, filename) )
                continue
            defaultLocaleFilename = filename
        
    defaultLocaleProperties = readPropertiesFromFile( os.path.join( resourceFiles, defaultLocaleFilename ) )
    defaultLocaleProperties.mergeWith(  )
        
    return incomingProperties
         
    
if __name__ == "__main__":
    main()    
    