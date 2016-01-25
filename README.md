
#Duplicate Image Finder in a directory

##What is this?

This is a java library to find duplicate Images in a directory recursively.

##How to use this library?

Download library from

	https://github.com/srch07/Duplicate-Image-Finder-API/blob/master/archives/duplicate_image_finder_1.0.jar

Add this library to your classpath.

In Code :

	List<List<String>> duplicateImagesPairs = DuplicateImageFinder.findDuplicatePairs("D:\\");
	List<String> allUniqueImages = DuplicateImageFinder.findAllUniqueImages("D:\\");
	List<String> allDuplicateImagesForDeletion = DuplicateImageFinder.findDuplicatesForDeletion("D:\\");

##What does each api do?
	DuplicateImageFinder.findDuplicatePairs("D:\\") : Finds all duplicate images, and groups all of them in pairs.
	DuplicateImageFinder.findAllUniqueImages("D:\\") : Finds all Unique images in directory recursively, and returns them as list.
	DuplicateImageFinder.findDuplicatesForDeletion("D:\\") : Finds all duplicate images in directory recursively, that you can delete.

Remember, API tries to keep best picture among the given duplicates based on height and width of images.

##How do I add this library, if using maven?
Create a folder inside "src" folder of your project called "lib", and copy the jar there.

Use below code to specify dependency in pom.xml

	<dependency>
		<groupId>duplicate_image_finder_1.0</groupId>
		<artifactId>duplicate_image_finder_1.0</artifactId>
		<scope>system</scope>
		<version>1.0</version>
		<systemPath>${basedir}\src\lib\duplicate_image_finder_1.0.jar</systemPath>
	</dependency>
