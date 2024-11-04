# Task 1.3: Thresholding
import cv2

# read in chosen enhanced image
image = cv2.imread("./output/histogram_equalised.jpg", cv2.IMREAD_GRAYSCALE)

# perform otsu thresholding to find the optimal threshold
threshold_value, otsu_thresholded = cv2.threshold(image, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
cv2.imwrite("./output/otsu.jpg", otsu_thresholded)

print("Threshold value used: " + str(threshold_value))
