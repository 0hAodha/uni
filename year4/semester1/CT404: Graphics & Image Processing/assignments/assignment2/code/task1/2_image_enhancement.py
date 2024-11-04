# Task 1.2: Image Enhancement
import cv2

# read in chosen single-channel greyscale image
image = cv2.imread("./output/g_channel_greyscale.jpg", cv2.IMREAD_GRAYSCALE)

# apply histogram equalisation
equalised_image = cv2.equalizeHist(image)
equalised_image_contrast = equalised_image.std()
cv2.imwrite("./output/histogram_equalised.jpg", equalised_image)

# apply contrast stretching
stretched_image = cv2.normalize(image, None, 0, 255, cv2.NORM_MINMAX)
stretched_image_contrast = stretched_image.std()
cv2.imwrite("./output/contrast_stretched.jpg", stretched_image)

print("Histogram Equalisation Contrast: " + str(equalised_image_contrast))
print("Contrast Stretching Contrast: "    + str(stretched_image_contrast))
