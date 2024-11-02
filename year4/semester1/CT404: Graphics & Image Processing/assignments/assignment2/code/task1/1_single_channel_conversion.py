# Task 1: A Morphological image processing pipeline for medical images
# Task 1.1: Conversion to a single channel image
import cv2

# read in original image (in BGR format)
image = cv2.imread("../../Task1.jpg")

# convert to greyscale
greyscale = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
cv2.imwrite("./output/greyscale.jpg", greyscale)

# convert to blue channel only
b_channel = image.copy()
b_channel[:, :, 1] = 0
b_channel[:, :, 2] = 0
cv2.imwrite("./output/b_channel.jpg", b_channel)

# convert blue channel to greyscale
b_channel_greyscale = cv2.cvtColor(b_channel, cv2.COLOR_BGR2GRAY)
b_channel_greyscale_contrast = b_channel_greyscale.std()
cv2.imwrite("./output/b_channel_greyscale.jpg", b_channel_greyscale)

# convert to green channel only
g_channel = image.copy()
g_channel[:, :, 0] = 0
g_channel[:, :, 2] = 0
cv2.imwrite("./output/g_channel.jpg", g_channel)

# convert green channel to greyscale
g_channel_greyscale = cv2.cvtColor(g_channel, cv2.COLOR_BGR2GRAY)
g_channel_greyscale_contrast = g_channel_greyscale.std()
cv2.imwrite("./output/g_channel_greyscale.jpg", g_channel_greyscale)

# convert to red channel only
r_channel = image.copy()
r_channel[:, :, 0] = 0
r_channel[:, :, 1] = 0
cv2.imwrite("./output/r_channel.jpg", r_channel)

# convert red channel to greyscale
r_channel_greyscale = cv2.cvtColor(r_channel, cv2.COLOR_BGR2GRAY)
r_channel_greyscale_contrast = r_channel_greyscale.std()
cv2.imwrite("./output/r_channel_greyscale.jpg", g_channel_greyscale)

# assess objectively which allows most contrast
print("Blue Channel Greyscale Contrast: "  + str(b_channel_greyscale_contrast))
print("Green Channel Greyscale Contrast: " + str(g_channel_greyscale_contrast))
print("Red Channel Greyscale Contrast: "   + str(r_channel_greyscale_contrast))
