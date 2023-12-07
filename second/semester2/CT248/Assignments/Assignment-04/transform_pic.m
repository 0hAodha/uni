function [img] = transform_pic(img)
% function which converts a 255 colour code to 0, 254 to 1, etc, and 0 to
% 255. 
    img = 255 - img;
end