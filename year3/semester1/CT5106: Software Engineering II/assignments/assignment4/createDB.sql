

DROP TABLE IF EXISTS `authors`;
CREATE TABLE IF NOT EXISTS `authors` (
  `authorid` int(11) NOT NULL AUTO_INCREMENT,
  `surname` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `yob` smallint(6) NOT NULL,
  `nationality` varchar(255) NOT NULL,
  `biography` longtext NOT NULL,
  PRIMARY KEY (`authorid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `authors`
--

INSERT INTO `authors` (`authorid`, `surname`, `firstname`, `yob`, `nationality`, `biography`) VALUES
(1, 'Steinbeck', 'John', 1902, 'American', 'John Ernst Steinbeck was an American writer. He won the 1962 Nobel Prize in Literature \"for his realistic and imaginative writings, combining as they do sympathetic humor and keen social perception\". He has been called \"a giant of American letters.\" '),
(2, 'Linklater', 'Eric', 1899, 'British', 'Eric Robert Russell Linklater CBE was a Welsh-born Scottish poet, fiction writer, military historian, and travel writer. For The Wind on the Moon, a children\'s fantasy novel, he won the 1944 Carnegie Medal from the Library Association for the year\'s best children\'s book by a British subject.');

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `bookid` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `coverimage` varchar(255) NOT NULL,
  `authorid` int(11) NOT NULL,
  PRIMARY KEY (`bookid`),
  KEY `authorid` (`authorid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`bookid`, `year`, `title`, `coverimage`, `authorid`) VALUES
(1, 1962, 'Travels with Charley: In Search of America', 'twc', 1),
(2, 1961, 'The Winter of Our Discontent', 'twood', 1),
(3, 1946, 'Private Angelo', 'pa', 2),
(4, 1952, 'Ripeness is All', 'ria', 2);
COMMIT;

