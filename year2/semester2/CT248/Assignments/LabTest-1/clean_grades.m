function [grades] = clean_grades(grades) 
   for i = 1:(size(grades,1)*size(grades,2))
       if grades(i) < 0 
           grades(i)  = 0; 
       elseif grades(i) > 100 
           grades(i) = 0; 
       end 
   end
end