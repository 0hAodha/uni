function [returnImg] = pic2grayscale(img) 
% which uses the NTSC Standard transformation to convert RGB to grayscale. 
    %0.2989 * R + 0.5870 * G + 0.1140 * B

    % img to be returned
    returnImg = zeros(size(img,1), size(img, 2)); 

    % looping through the RGB image and calculating the grayscale value for
    % each pixel in the corresponding returnImg
    for i = 1:size(img,1) 
        for j = 1:size(img,2)
            returnImg(i,j) = (0.2989 * img(i,j,1)) + (0.5870 * img(i,j,2)) + (0.1140 * img(i,j,3));
        end 
    end
    
    returnImg = uint8(returnImg);
end