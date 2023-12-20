takes(tom, ct331). 
takes(mary, ct331). 
takes(joe, ct331). 
takes(tom, ct345). 
takes(mary, ct345). 
instructs(bob, ct331). 
instructs(ann, ct345).  

% 1. rule that returns true if a given instructor teaches a given student
teaches(Instructor, Student) :- instructs(Instructor, Course), takes(Student, Course).

% 2. query that uses the `teaches` rule to show all students instructed by bob
?- teaches(bob, Student).
?- findall(Student, teaches(bob, Student), Students).

% 3. query that uses the `teaches` rule to show all instructors that instruct mary
?- teaches(Instructor, mary).
?- findall(Instructor, teaches(Instructor, mary), Instructors).

% 5. rule that returns true if two students take the same course
takesSameCourse(Student1, Student2) :- takes(Student1, Course), takes(Student2, Course).

contains1(Element, [Element | Tail]).


contains2(Sublist, [Head | Sublist]).
