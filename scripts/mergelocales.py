'''
Created on 2010-02-25

@author: beaudoin
'''

import os, re


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
    
    def __init__(self):
        '''
        Initializes a new property.
        '''
        self._comments = None
        self._key = None
        self._value = None
        
    def getFromFile(self, file):
        '''
        Get the next property from a file. Throws a InvalidProperty exception
        if the property is not correctly formatted. This method will replace the
        comments, key and value of this Property object.
        
        @param file: The opened file object to read from.
        @return: True on success, 
                 An empty string if EOF is encountered,
                 The entire non-UIBinder section (including leading comments) if no property is 
                 found but this section is encountered. 
                 The non-UIBinder section is indicated by a comment line starting with ###.
        '''
        
        # Skip blank lines. Return with false if EOF or non-UIBinder section is reached
        line = file.readNonBlankLine()
        if not line or line.startswith('###'):
            nonUIBinderSection = line
            while line:
                nonUIBinderSection += file.readline()
            return nonUIBinderSection 

        # Read the comment block
        self._comments = ''
        while line.startswith('#'):
            self._comments += line
            line = file.readline()

        # Read the key/value
        index = line.find('=')
        if index < 0 :
            raise InvalidProperty( 'No key=value found. File: %s.\nComment block: %s' % ( file.name, self._comments ) )        
        
        key = line[:index].strip()
        if len(key) == 0:
            raise InvalidProperty( 'Invalid key found. File: %s.\nLine: %sComment block: %s' % ( file.name, line, self._comments ) )
            
        value = line[index+1:]
        while value.endswith('\\\n'):
            value += file.readline()
        
        return True
    
    def setDeprecated(self):
        '''
        Ensures that this property is marked as deprecated, by including an appropriate comment.
        '''
        deprecatedComment = '# DEPRECATED\n'

        if not self._comments.find( deprecatedComment ):
            self._comments += deprecatedComment
        
    def __str__(self):
        return "Comment block: %sKey: %sValue: %s\n" % ( self._comments, self._key, self._value ) 

class PropertyCollection(object):
    '''
    A collection of Property object that can be merged or written to files 
    '''
    
    def __init__(self, locale):
        '''
        Initializes a new collection of Property objects.
        
        @param locale: The locale of this collection. Use the empty string for the default locale.
        '''
        self._locale = locale
        self._properties = {}
    
    def add(self, property):
        '''
        Adds an object of type Property to the collection
        
        @param property: The Property object to add
        '''
        self._properties[ property._key ] = property 
    
    def mergeWith(self, otherCollection):
        '''
        Merge this collection with another one. Any key that is found in the other collection but not
        in this one will be added. Any key that is found in this collection but not in the other one
        will be marked as deprecated.  
        
        @param otherCollection: The collection to merge into this one.
        '''
        
        # Bring properties over
        for (key, property) in otherCollection._properties.iteritems():
            if not self._properties.has_key(key):
                self._properties[key] = property

        # Find deprecated properties
        for (key, property) in self._properties.iteritems():
            if not otherCollection._properties.has_key(key):
                property.setDeprecated()

        
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


def enumeratePropertyFiles( path ):
    '''
    Looks in the specified directory for all property files, that is, files inding in .properties.
     
    @param path: The directory to look in.
    @return: A list of files.
    '''

    return filter( lambda filename: filename.endswith('.properties'),
                   os.listdir(path) ) 

def readPropertiesFromFile( filename ):
    '''
    Read all the properties from a given file.
    
    @param filename: The name of the file to read properties from.
    @return: A tuple ( leadingComments, properties, nonUIBinderSection ). Where:
             leadingComments are comments found at the top of the file (a string).
             properties is a PropertyCollection containing all Property objects found in the file.
             nonUIBinderSection is the block following the properties not generated by UIBinder, see Properties.getFromFile (a string).
    '''
    
    # Identify the locale from the filename
    locale = ''  # Default locale when none is found
    match = re.match( r'[^_]*_([^\.]*)\.properties', filename )
    if match:
        locale = match.group(1)
        
    file = os.open(filename, 'r')
    
    # Read the leading comments
    leadingComments = ''
    pos = file.tell()
    line = file.readline()
    while line.startswith('#') or re.match( r'\s*\n', line, re.L ):
        leadingComments += line
        pos = file.tell()
        line = file.readline()
    file.seek(pos)
    
    properties = PropertyCollection( locale )
    while(True):
        property = Property()
        result = property.getFromFile( file )
        if result is not True:
            # result contains the non-UIBinder section
            nonUIBinderSection = result
            break
        properties.add(property)
    
    return ( leadingComments, properties, nonUIBinderSection )
    
    