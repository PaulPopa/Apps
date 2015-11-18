{-# LANGUAGE CPP #-}
{-# LANGUAGE Safe #-}
module Exercise where

import Data.List hiding (find)


-- Exercise set 2.
--
-- 30% of the exercises are intended to be rather challenging, and
-- will allow you to get a mark above 69%, in conjunction with the
-- other available exercises, so as to get a 1st class mark. To get
-- II.2, you will need to do enough hard exercises, in addition to the
-- medium and easy ones. 
--
--      >= 70% 1st
--      >= 60% II.1
--      >= 50% II.2
--      >= 40% III
--      <= 39% fail
--
-- 
-- Do as many unassessed exercises as you can, as they should make the
-- assessed exercises easier.
--
-- You are allowed to use the functions available in the standard
-- prelude (loaded by default by ghc and ghci). You should not need to
-- use other Haskell libraries from the Haskell platform available in
-- the lab, but you are allowed to use them if you wish. However, in
-- your final submission, you should not use any IO facilities, as
-- this won't compile with the marking script.

-- This exercise set revolves around a directory tree on a computer,
-- and some Unix-like functions to manipulate them.
--
-- This exercise doesn't involve reading or writing actual files from
-- disk. Instead, we represent them internally in Haskell using a
-- "data" definition, explained below.
--
-- We have these data types:
--
--     - Entry: Can be either a file or a directory (with sub-Entries)
--     - EntryName: exactly the same as a string; 
--                  represents a directory or file name
--     - Path: exactly the same as a list of strings
--     - FileProp: file properties 

data Entry = File EntryName FileProp
           | Dir EntryName [Entry]
  deriving (Show, Eq, Read)

-- The name of a file
type EntryName = String

-- A sequence of file name components.
--
-- A path is a list of strings used to navigate down to a subdirectory
-- of a given directory. We start from an Entry, and we end up with a
-- sub-Entry, provided the path is valid. See the example in the "cd"
-- exercise below.
type Path = [String]

-- FileProp describes some attributes of a file.
--
-- The components mean size, content, and creation time,
-- in that order.
data FileProp = FP Int String Int
  deriving (Show, Eq, Read)


-- Exercise, easy. Create a FileProp that describes a file with size 3,
-- content "abc", and time 4.

exampleFP :: FileProp
exampleFP = FP 3 "abc" 4

{-

Entries describe directories and files in the file system. For instance, the
following entry describes an empty directory:

    Dir "somedirname" []

The following entry describes a file in the filesystem:

    File "somefilename" (FP 4 "xyz" 3)

The following entry describes a more complicated directory.

    Dir "uni" [File "marks.txt" (FP 1036 "..." 2014),
               Dir "examSheets" [],
               File "address.txt" (FP 65 "..." 2010)
              ]

-}

-- Exercise, easy. Create three entries that correspond to the following trees:
--
-- 1.   todo.txt, size 723, time 2015, content "do fp exercises"
--
-- 2.   empty-directory
--      |
--
-- 3.   hard drive
--      |
--      |-- WINDOWS
--      |   |
--      |   |-- cmd.exe, size 1024, time 1995, content ""
--      |   |
--      |   |-- explorer.exe, size 2048, time 1995, content ""
--      |
--      |-- Documents
--      |   |
--      |   |-- User1
--      |   |   |
--      |   |   |-- recipe.doc, size 723, time 2000
--      |   |
--      |   |-- User2
--      |   |   |
--
-- You must pay attention to the order of entries in a directory.
--
-- There is a dash in the directory name of exampleEntry2.


exampleEntry1 :: Entry
exampleEntry1 = File "todo.txt" (FP 723 "do fp exercises" 2015)
exampleEntry2 :: Entry
exampleEntry2 = Dir "empty-directory" []
exampleEntry3 :: Entry
exampleEntry3 = Dir "hard drive" [Dir "WINDOWS" [File "cmd.exe" (FP 1024 "" 1995),File "explorer.exe" (FP 2048 "" 1995)],Dir "Documents" [Dir "User1" [File "recipe.doc" (FP 723 "" 2000)],Dir "User2" []]]

-- Exercise, unassessed. You're given a directory as a value of type
-- Entry. In this directory there is a subdirectory with name n. Find
-- (and return) this subdirectory.

getEntryName :: Entry -> EntryName
getEntryName (File entryName _) = entryName
getEntryName (Dir entryName []) = entryName
getEntryName (Dir entryName (entry)) = entryName

getEntry :: Entry -> [Entry]
getEntry (Dir _ (entry)) = entry
getEntry (File _ (FP _ _ _ ) ) = []

getFileName :: Entry -> String
getFileName (File entryName _) = entryName

cd1 :: Entry -> String -> Maybe Entry
cd1 (Dir _ []) fileName = Nothing
cd1 entry fileName 
	| getEntryName(head(getEntry(entry))) == fileName = Just (head(getEntry(entry))) 
	| otherwise = Nothing

-- Exercise, easy. As before, but you need to navigate not one but
-- possibly many steps down; consecutive directory names are given in
-- the list of strings.
-- 
-- Example: Given the entry in the following drawing
-- 
--     root
--     |
--     |-- dir1
--     |   |
--     |   |-- dir1a
--     |   |   |
--     |   |   |-- dir1a1
--     |   |   |
--     |   |   |-- dir1a2
--     |   |
--     |   |-- dir1b
--     |
--     |-- dir2
--     |   |
--     |   |-- dir1a
--     |   |
--     |   |-- dir1b
--     |
--     |-- file3
-- 
-- and the path ["dir1", "dir1a"], you need to return the Entry that
-- contains dir1a1 and dir1a2.
-- 
-- If there is no such entry, return Nothing. If there is such an entry,
-- return Just that entry.
-- 
-- You can assume that there will be at most one entry with the given path.

cd :: Entry -> Path -> Maybe Entry
cd root [] = Just root
cd (Dir entryName []) [""] = Just (Dir entryName []) 
cd (Dir entryName entry) [""] = Just (Dir entryName entry)
cd (Dir entryName []) path = Nothing
cd (File _ _) [_] = Nothing
cd root path = if head(equals (getEntry(root)) (path)) == True then cd (head(getEntry(root))) (tail(path)) 
							       else cd (whenDroppedRoot) (path)
   							       where whenDroppedRoot = Dir (getEntryName(root)) (drop 1 (getEntry(root)))

equals :: [Entry] -> [EntryName] -> [Bool]
equals [] path = [False]
equals root path = (getEntryName(head(root)) == head(path)) : equals (drop 1 root) path

-- Exercise, medium. Split a string representing a path into its
-- components. The components are separated by (forward) slashes.
-- Hint: the prelude functions for lists will be helpful here, but you
-- are not required to use them.
--
-- Examples:
--
--     explode "abc/de/fghi" = ["abc", "de", "fghi"]
--     explode "abc//fghi" = ["abc", "", "fghi"]
--
-- It is a matter of convention whether we prefer
--     explode "" = [] 
-- or
--     explode "" = [""]
-- Choose your own convention. Both will be considered to be correct.


split :: Eq a => a -> [a] -> [[a]]
split char [] = []
split char string = let (x,xs) = span (/=char) string in
		    x : split char (drop 1 xs)

myExplode :: String -> Path
myExplode s = split '/' s

explode :: String -> Path
explode s = myExplode s ++ if last(s) == '/' then [""] else []

-- Exercise, easy. The "inverse" of explode: combine components with
-- slashes to a single String.
--
-- For every string s, you must have
--
--    implode (explode s) = s
--
-- You may want to use the functions "concat", "intersperse", and/or
-- "intercalate"; the latter two are from package Data.List.

implode :: Path -> String
implode path = concat(intersperse ['/'] path)

-- Exercise, easy. Given an Entry representing a directory, print out
-- a directory listing in a format similar to "ls -l" on Unix.
--
-- The required format is as in the following example:
--
--     size: 420 time: 5 filename1
--     size: 5040 time: 200 other.txt
--     size: 30 time: 36 filename2
--
-- You need to separate every line with a newline ('\n') character,
-- and also put a newline at the end.
--
-- Keep the files in their order in the given Entry.
--
-- You do not need to convert units, just print the numbers.

printFirst :: Entry -> String
printFirst (File entryName (FP size content time)) = "size: " ++ show(size) ++ " time: " ++ show(time) ++ " " ++  entryName ++ "\n"
printFirst entry = getEntryName(entry) ++ "\n" 

lsL :: Entry -> String
lsL (Dir _ []) = "\n"
lsL (File entryName (FP size content time))  = "size: " ++ show(size) ++ " time: " ++ show(time) ++ " " ++  entryName
lsL entry = firstElement ++ lsL1 newEntry 
		where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry))) 
		      firstElement = printFirst(head(getEntry(entry)))

lsL1 :: Entry -> String
lsL1 (Dir _ []) = ""
lsL1 entry = firstElement ++ lsL1 newEntry 
		where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry))) 
		      firstElement = printFirst(head(getEntry(entry)))


-- Exercise, medium. List all the files in a directory tree. Sample
-- output:
--
--    root
--    |
--    |-- dir1
--    |   |
--    |   |-- dir1a
--    |   |   |
--    |   |   |-- dir1a1
--    |   |   |
--    |   |   |-- somefile
--    |   |   |
--    |   |   |-- dir1a2
--    |   |
--    |   |-- dir1b
--    |
--    |-- file2
--    |
--    |-- dir3
--    |   |
--    |   |-- dir3a
--    |   |
--    |   |-- dir3b
--
--
-- You can assume that the entry represents a directory.
--
-- Use the newline convention as given above.

lsTree1 :: Entry -> String
lsTree1 (File entryName fp) = entryName
lsTree1 (Dir entryName []) = entryName
lsTree1 root = lsTreeWithIndent root 1

createLine :: Int -> String
createLine 1 = char where char = "|--"
createLine n = char ++ "   " ++ createLine (n-1)
		   where char = "|"

createLineWithoutDashes :: Int -> String
createLineWithoutDashes 1 = char where char = "|"
createLineWithoutDashes n = char ++ "   " ++ createLineWithoutDashes (n-1) 
			  	where char = "|"

indent :: Int -> String
indent 0 = ""
indent n = createLineWithoutDashes n ++ "\n" ++ createLine n

takeLength entry = length(lsTree1(entry))
lsTree :: Entry -> String
lsTree (Dir entryName []) = entryName ++ "\n"
lsTree (File entryName fp) = entryName ++ "\n"
lsTree entry = drop 1 (lsTree1(entry))



lsTreeWithIndent :: Entry -> Int -> String
lsTreeWithIndent (File entryName (FP _ _ _ ) ) size = " " ++ entryName ++ "\n"
lsTreeWithIndent (Dir entryName []) size = " " ++ entryName  ++ "\n"  
lsTreeWithIndent entry size = " " ++ getEntryName(entry) ++ "\n" ++  indent(size) ++  lsTreeWithIndent (head(getEntry(entry))) (size+1) ++ lsTreeWithIndent2 newEntry size 
								where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry)))
	
lsTreeWithIndent2 :: Entry -> Int -> String
lsTreeWithIndent2 (File entryName (FP _ _ _ ) ) size = ""
lsTreeWithIndent2 (Dir entryName []) size = ""										
lsTreeWithIndent2 entry size = indent(size) ++  lsTreeWithIndent (head(getEntry(entry))) (size+1) ++ lsTreeWithIndent2 newEntry size 
								where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry)))


-- Exercise, challenge. Make a list of all the files and directories
-- recursively in a tree (similar to "find ." in linux). If the
-- argument fullPath is False, every entry in the returned list will
-- have only the bare directory or file name. If fullPath is True,
-- every entry is the path towards that entry,
-- e.g. "root/subdir1/subdir1a/file".
--
-- The root must be the first list item. The output will be in the
-- same order as for lsTree.
--
-- For example, if d is this directory from an earlier exercise:
--
--      hard drive
--      |
--      |-- WINDOWS
--      |   |
--      |   |-- cmd.exe, size 1024, time 1995, content ""
--      |   |
--      |   |-- explorer.exe, size 2048, time 1995, content ""
--      |
--      |-- Documents
--      |   |
--      |   |-- User1
--      |   |   |
--      |   |   |-- recipe.doc, size 723, time 2000
--      |   |
--      |   |-- User2
--      |   |   |
--
-- then we have

--       listAll False d =
--                ["hard drive", "WINDOWS", "cmd.exe", "explorer.exe"
--                ,"Documents", "User1", "recipe.doc", "User2"]
-- and
--      listAll True d = 
--                ["hard drive"
--                ,"hard drive/WINDOWS"
--                ,"hard drive/WINDOWS/cmd.exe"
--                ,"hard drive/WINDOWS/explorer.exe"
--                ,"hard drive/Documents"
--                ,"hard drive/Documents/User1"
--                ,"hard drive/Documents/User1/recipe.doc",
--                ,"hard drive/Documents/User2"]

listAllFalse :: Bool -> Entry -> [String]
listAllFalse False (Dir entryName []) = [entryName]  
listAllFalse False (File entryName (FP _ _ _) ) = [entryName]
listAllFalse False entry = [getEntryName(entry)] ++ listAllFalse False (head(getEntry(entry))) ++ listAllFalse2 False newEntry 
				where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry)))
					
listAllFalse2 :: Bool -> Entry -> [String]	
listAllFalse2 False (Dir entryName []) = []
listAllFalse2 False (File entryName (FP _ _ _) ) = [entryName]										
listAllFalse2 False entry = listAllFalse False (head(getEntry(entry))) ++ listAllFalse2 False newEntry 
				where newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry)))

getType :: Entry -> String
getType (Dir entryName _) = "Dir"
getType (File entryName (FP _ _ _)) = "File"

listAllTr1 :: Bool -> Entry -> String -> [String]
listAllTr1 True (Dir entryName []) firstPath = []
listAllTr1 True (File entryName fp) firstPath = []
listAllTr1 True entry firstPath = let secondPath = firstPath ++ "/" ++ (getEntryName(head(getEntry(entry))))
				      newEntry = Dir (getEntryName(entry)) (drop 1 (getEntry(entry)))
				  in
				  if(getType(head(getEntry(entry))) == "Dir") then [secondPath] ++ listAllTr1 True (head(getEntry(entry))) (secondPath) ++ listAllTr1 True newEntry firstPath
      									      else [firstPath ++ "/" ++ getEntryName(head(getEntry(entry)))] ++ listAllTr1 True newEntry firstPath

listAllTr2 :: Bool -> Entry -> [String]
listAllTr2 True (Dir entryName []) = [entryName]
listAllTr2 True (File entryName fp) = [entryName]
listAllTr2 True entry = let firstPath = (getEntryName(entry)) ++ "/" ++ getEntryName(head(getEntry(entry)))
			    newEntry = Dir (getEntryName (entry)) (drop 1 (getEntry(entry)))
			    secondPath = getEntryName(entry)
			    in
			    [(getEntryName(entry))] ++ [firstPath] ++ listAllTr1 True (head(getEntry(entry))) (firstPath) ++ listAllTr1 True newEntry secondPath
				

listAll :: Bool -> Entry -> [String]
listAll fullPath root 
	| fullPath == False = listAllFalse False root
	| fullPath == True = listAllTr2 True root

-- Exercise, hard. 
--
-- Given a tree, insert a given subtree in a certain position.
--
-- It does not matter how the inserted subtree is ordered with respect
-- to the other items in the directory where it is inserted. That is,
--
--     cp (Dir "root" [Dir "subdir1" [Dir "subdir1a" []]]) (["subdir1"], Dir "subdir1b" [])
--
-- may return either
--
--     Dir "root" [Dir "subdir1" [Dir "subdir1a" [], Dir "subdir1b" []]]
--
-- or
--
--     Dir "root" [Dir "subdir1" [Dir "subdir1b" [], Dir "subdir1a" []]] .
--
-- (This function is similar-ish to the Unix 'cp' utility.)

cp :: Entry -> (Path, Entry) -> Entry
cp (File entryName (FP _ _ _)) (_,_) = error "Can't insert tree in a File"
cp root ([], subTree) = Dir (getEntryName(root)) (getEntry(root) ++[subTree])
cp root (headPath:tailPath, subtree) = Dir (getEntryName(root)) (subEntries)
	where
	subEntries :: [Entry]
	subEntries = map (checkEntry) (getEntry(root))
	checkEntry :: Entry -> Entry
	checkEntry entry = if getEntryName(entry) == headPath then cp entry (tailPath, subtree) else entry

-- Exercise, medium. Given a tree and a path, remove the file or
-- directory at that path.
--
-- You can assume that there is a file or directory at that path. If
-- there are multiple files or directories with that path, you need to
-- remove all of them.
--
-- (In that the case, the tree would not be "valid" according to isValid.)

rm :: Entry -> Path -> Entry
rm root [] = root
rm root path = remove root path

remove :: Entry -> Path -> Entry
remove root [] = Dir (getEntryName(root)) []
remove root (headPath:tailPath) = Dir (getEntryName(root)) (subEntries)
	where 
	subEntries :: [Entry]
	subEntries = removeEntry (Dir (last(headPath:tailPath)) []) (map (checkDelete) (getEntry(root)))
	checkDelete :: Entry -> Entry
	checkDelete entry = if getEntryName(entry) == headPath then remove entry (tailPath) else entry

removeEntry :: Entry -> [Entry] -> [Entry]
removeEntry _ []                				        = []
removeEntry entry (headSubEntry:tailSubEntries) | entry == headSubEntry = removeEntry entry tailSubEntries
                    	 	  	        | otherwise 		= headSubEntry : removeEntry entry tailSubEntries	

-- Exercise, harder. Return a tree with all the same entries, but so
-- that the entries of each (sub)directory are in sorted order.
--
-- You may use the function `sort` from the Prelude.
--
-- If there are multiple entries with the same name in a directory,
-- you may choose any order.

ordering :: Entry -> Entry -> Ordering
ordering firstEntry secondEntry = if (firstName > secondName) then GT else LT
				where firstName = getEntryName(firstEntry)
				      secondName = getEntryName(secondEntry)

sortTree :: Entry -> Entry
sortTree (Dir entryName []) = Dir entryName []
sortTree (File entryName fp) = File entryName fp
sortTree entry = Dir (getEntryName(entry)) (sortBy ordering (map sortTree (getEntry(entry))))

-- Exercise, unassessed. Change all letters to upper case in a string.
--
-- For instance,
--
--     upcaseStr "!someString123" = "!SOMESTRING123"
--
-- Hint: look at the definition of the String type in the Prelude, and think
-- about functions related to that type.
--
-- You may use the function upcaseChar, defined below.

upcaseStr :: String -> String
upcaseStr [] = []
upcaseStr s = [upcaseChar(head(s))] ++ upcaseStr(tail(s))

upcaseChar :: Char -> Char
upcaseChar c =
    if ('a' <= c && c <= 'z')
    then toEnum (fromEnum c + fromEnum 'A' - fromEnum 'a')
    else c

-- Exercise, harder. Change all the file names (as above) and their
-- properties, similar to the above exercise.
--
-- From the type of modifyEntries, you can see what the input of
-- fileMap must be.

firstOfTuple (a,b) = a
secondOfTuple (a,b) = b

getFileSize :: FileProp -> Int
getFileSize (FP size _ _ ) = size

getFileContent :: FileProp -> String
getFileContent (FP _ content _ ) = content

getFileTime :: FileProp -> Int
getFileTime (FP _ _ time) = time

getFileProp :: Entry -> FileProp
getFileProp (File _ fileProp) = fileProp

fileMap :: (EntryName, FileProp) -> (EntryName, FileProp)
fileMap (entryName, fileProp) = ((upcaseStr(entryName)), (FP (getFileSize(fileProp)) (upcaseStr(getFileContent(fileProp))) (getFileTime(fileProp))))

fileMap2 :: (EntryName, FileProp) -> (EntryName, FileProp)
fileMap2 (entryName, fileProp) = ((entryName), (FP 10 "eusuntbosulbosilor" 10))

getEntryAfterMap2 :: Entry -> ((EntryName, FileProp) -> (EntryName, FileProp)) -> Entry
getEntryAfterMap2 entry fileMap = if(getType(entry)) == "File" then File (firstOfTuple(fileMap( (getEntryName(entry)), (getFileProp(entry)) ))) 
							        (secondOfTuple(fileMap( (getEntryName(entry)), (getFileProp(entry)) )))
						      else Dir (getEntryName(entry)) (map (\entry -> (getEntryAfterMap2 entry fileMap)) (getEntry(entry)))

modifyEntries :: Entry -> ((EntryName, FileProp) -> (EntryName, FileProp)) -> Entry
modifyEntries root fileMap = getEntryAfterMap2 root fileMap;

-- Exercise, unassessed. Create a "Fibonacci tree".
--
-- The Fibonacci tree for n=3 looks like this:
--
--     dir3
--     |   
--     |-- dir2
--     |   |
--     |   |-- dir1
--     |   |   |
--     |   |   |-- file, size 0, time 0, content 0
--     |   |   
--     |   |
--     |   |-- file, size 0, time 0, content 0
--     |
--     |-- dir1
--     |   |
--     |   |-- file, size 0, time 0, content 0
--
--
--
-- The Fibonacci tree (fibCreate 0) is a file with name "file", size and time
-- 0, and content "". For n >= 1, fibCreate n is a directory named "dir{n}",
-- containing precisely fibCreate (n-1) and fibCreate (n-2). Exception:
-- fibCreate 1 contains only fibCreate 0, and not fibCreate (-1).
--
-- (We just made up the concept of Fibonacci trees, it is not a real thing.)

fibCreate :: Int -> Entry
fibCreate 0 = File "file" (FP 0 "" 0)
fibCreate 1 = Dir "dir1" [fibCreate 0]
fibCreate maxLevel = Dir ("dir" ++ (show(maxLevel))) [fibCreate(maxLevel-1), fibCreate(maxLevel-2)]



-- Exercise, unassessed. Make the following infinite tree:
--
--     all
--     |
--     |-- file, size 0, time 0, content 0
--     |
--     |-- dir1
--     |   |
--     |   |-- file, size 0, time 0, content 0
--     |
--     |-- dir2
--     |   |
--     |   |-- dir1
--     |   |   |
--     |   |   |-- file, size 0, time 0, content 0
--     |
--     |-- dir3
--     |   |
--     |   |-- dir2
--     |   |   |
--     |   |   |-- dir1
--     |   |   |   |
--     |   |   |   |-- file, size 0, time 0, content 0
--     |   |
--     |   |-- dir1
--     |   |   |
--     |   |   |-- file, size 0, time 0, content 0
--     |
--     |-- dir4
--     |   |
--     |   (and so on)
--     |
--     | ...
--
--
-- It is to be expected that computations such as (size fibEntry) will
-- not return a result and loop for ever (or until we run out of
-- memory). But you can still e.g. "cd" into such a tree.

fibHelper :: Int -> Entry
fibHelper n = Dir "all" (fibEntries n)

fibEntries :: Int -> [Entry]
fibEntries n = [fibCreate n] ++ (fibEntries(n+1))

fibEntry :: Entry
fibEntry = fibHelper 0

-- Exercise, unassessed. Remove from a tree all files that are larger
-- than a certain size. You should not remove any directories.
--
-- Files that are exactly that size should be kept in.

findSmallerThan :: Entry -> Int -> Entry
findSmallerThan root maxSize = Dir (getEntryName(root)) (map (myF) (getEntry(root)))
				where myF entry = if (getType(entry) == "File") then if ( getFileSize(getFileProp(entry)) > maxSize) then File "w" (FP 2 "w" 2) else entry
										 else entry
				


-- Exercise, challenge. Remove from a tree all files that do not
-- satisfy a given predicate. You should not remove any directories.

unbreakableCode = File "unbreakableCodeForSure" (FP (1) ("unbreakableContentForSure") (-10))

transformEntry :: Entry -> ((EntryName, FileProp) -> Bool) -> Entry
transformEntry root predicate 
		| getType(root) == "File" = if ( predicate(getEntryName(root), (getFileProp(root))) ) == False then unbreakableCode else root
		| getType(root) == "Dir" = Dir (getEntryName(root)) (map (\root -> (transformEntry root predicate)) (getEntry(root)))

removeEnt (File entryName fp) = File entryName fp
removeEnt root = Dir (getEntryName(root)) (removeEntry unbreakableCode (map (removeEnt) (getEntry(transformEntry (root) (predicate)))))

removeEnt2 (File entryName fp) predicate = File entryName fp
removeEnt2 root predicate = Dir (getEntryName(root)) (removeEntry unbreakableCode (map (\root -> (removeEnt2 root predicate)) (getEntry(transformEntry (root) (predicate)))))

predicate :: (EntryName, FileProp) -> Bool
predicate (entryName, fileProp) = (entryName, fileProp) == ("csa", (FP 10 "a" 151))

find :: Entry -> ((EntryName, FileProp) -> Bool) -> Entry
find root predicate = removeEnt2 root predicate 

-- Exercise, unassessed. Given a maximum file size, a file name and its file
-- properties, return whether the file is at most that size.
--
-- (This function gets a lot of information that it doesn't need; the
-- extra arguments are so that you can easily use `findSmallerThanPred
-- maxSize` as the predicate argument to `find`, in the next
-- exercise.)

findSmallerThanPred :: Int -> ((EntryName, FileProp) -> Bool)
findSmallerThanPred maxSize (filename, props) = undefined

-- Exercise, unassessed. Same as findSmallerThan, but implement it again
-- using `find` and `findSmallerThanPred`.

findSmallerThan2 :: Entry -> Int -> Entry
findSmallerThan2 root maxSize = undefined

-- Exercise, challenge, assessed.
--
-- List all directory and file names in the current directory in a
-- table. The table can be at most termWidth cells wide. You need to
-- use as few rows as possible, while separating columns with 2
-- spaces.
--
-- (This is similar to the Unix utility 'ls'.)
-- 
-- For instance, for terminal width 80, you might have the following
-- output:
--
--     a  d  g  j                zbcdefghijklmnc  zbcdefghijklmnf  zbcdefghijklmni
--     b  e  h  zbcdefghijklmna  zbcdefghijklmnd  zbcdefghijklmng
--     c  f  i  zbcdefghijklmnb  zbcdefghijklmne  zbcdefghijklmnh
--
--
-- The ordering is alphabetical by column, and the columns should be
-- indented as above.  You can assume that the longest directory/file
-- name is at most as long as the terminal is wide.
--
-- The first argument is the terminal width:

createMiniLists :: [EntryName] -> Int -> [[EntryName]]
createMiniLists [] nrLines = []
createMiniLists entryNames nrLines = [take (nrLines) (sort(entryNames))] ++ createMiniLists (drop (nrLines) (sort(entryNames))) nrLines;

maxColumnElem [] = []
maxColumnElem list = [maximum(map (length) (head(list)))] ++ maxColumnElem (tail(list))

addSpaces [] _ = ""
addSpaces transposedNames maximumOfMiniList = head(transposedNames) ++ putBlankSpaces(head(maximumOfMiniList) - (length(head(transposedNames)))) ++ "  "
						++ addSpaces (tail(transposedNames)) (tail(maximumOfMiniList))

eliminateLastSpace transposedNames maximumOfMiniList = take (length(addSpaces transposedNames maximumOfMiniList) - 2) (addSpaces transposedNames maximumOfMiniList)

getSubDirNames :: Entry -> [EntryName]
getSubDirNames entry = map (getEntryName) (getEntry(entry))

putBlankSpaces :: Int -> String
putBlankSpaces 0 = ""
putBlankSpaces 1 = " "
putBlankSpaces n = " " ++ putBlankSpaces(n-1)

mapAddSpaces transposedNames maximumOfMiniList entry termWidth lines = map (\transposedNames -> (eliminateLastSpace transposedNames maximumOfMiniList)) (transpose(createMiniLists(getSubDirNames(entry)) lines))

findLines transposedNames maximumOfMiniList entry termWidth lines = if (length(head(map(\transposedNames -> (eliminateLastSpace transposedNames maximumOfMiniList)) (transpose(createMiniLists(getSubDirNames(entry)) lines)))) > termWidth) then findLines transposedNames1 maximumOfMiniList1 entry termWidth (lines + 1) else mapAddSpaces transposedNames maximumOfMiniList entry termWidth lines
		where transposedNames1 = transpose(createMiniLists (getSubDirNames(entry)) (lines +1))
		      maximumOfMiniList1 = maxColumnElem (createMiniLists (getSubDirNames(entry)) (lines+1))

listInOrder transposedNames maximumOfMiniList entry termWidth lines = intercalate "\n" (findLines transposedNames maximumOfMiniList entry termWidth lines)

ls :: Int -> Entry -> String
ls termWidth entry = listInOrder transposedNames maximumOfMiniList entry termWidth 1 ++ "\n"
		where transposedNames = transpose(createMiniLists (getSubDirNames(entry)) 1)
		      maximumOfMiniList = maxColumnElem (createMiniLists (getSubDirNames(entry)) 1)

-- End of exercise set 2.

