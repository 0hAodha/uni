clear;
eng1 = imread("Engineering-Building.jpg");
eng1_gs = pic2grayscale(eng1);
eng1_gs_inv = transform_pic(eng1_gs);
eng1_gs_bin_50 = transform_threshold(eng1_gs,50);
eng1_gs_bin_75 = transform_threshold(eng1_gs,75);
eng1_gs_bin_100 = transform_threshold(eng1_gs,100);

% plotting images
subplot(3,2,1),imshow(eng1),title("Original Picture"); 
subplot(3,2,2),imshow(eng1_gs),title("Greyscale"); 
subplot(3,2,3),imshow(eng1_gs_inv),title("Inverted Greyscale"); 
subplot(3,2,4),imshow(eng1_gs_bin_50),title("Binary Threshold = 50"); 
subplot(3,2,5),imshow(eng1_gs_bin_75),title("Binary Threshold = 75"); 
subplot(3,2,6),imshow(eng1_gs_bin_100),title("Binary Threshold = 100"); 
