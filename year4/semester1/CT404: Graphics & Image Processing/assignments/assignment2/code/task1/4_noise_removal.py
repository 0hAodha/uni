# Task 1.4: Noise Removal
import cv2

# read in thresholded image
image = cv2.imread("./output/otsu.jpg", cv2.IMREAD_GRAYSCALE)

# try several different sizes of structuring element (must be odd)
for kernel_size in range(1, 16, 2):
    # define a disk-shaped structuring element
    structuring_element = cv2.getStructuringElement(cv2.MORPH_ELLIPSE, (kernel_size, kernel_size))

    # apply morphological opening to remove noise
    opened_image = cv2.morphologyEx(image, cv2.MORPH_OPEN, structuring_element)
    cv2.imwrite(f"./output/kernel_size_{kernel_size}.jpg", opened_image)
