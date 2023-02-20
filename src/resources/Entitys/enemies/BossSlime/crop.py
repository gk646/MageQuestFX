from PIL import Image
import os
import sys


def split_image(image_path, num_tiles_x,num_tiles_y , tile_size, folderName):
    # Open the image
    image = Image.open(image_path)

    # Calculate the number of tiles in each dimension
    num_tiles_y =num_tiles_y

    # Loop over the tiles and save them
    for tile_y in range(num_tiles_y):
        for tile_x in range(num_tiles_x):
            # Calculate the pixel coordinates of the tile
            x = tile_x * tile_size
            y = tile_y * tile_size
            x2 = x + tile_size
            y2 = y + tile_size

            # Crop the tile from the image
            tile = image.crop((x, y, x2, y2))

            # Save the tile in the tiles folder
            folder_name = folderName
            if not os.path.exists(folder_name):
                os.makedirs(folder_name)
            filename = os.path.join(folder_name, f"{tile_y*num_tiles_x + tile_x}.png")
            tile.save(filename)

split_image("pls.png",10,1,145,"walk")

def hel():
    print("Usage: crop.py image_path num_tiles_x num_tiles_y tile_size folderNames")
    print("  image_path: The path to the input image file.")
    print("  num_tiles_x: The number of tiles to create in the X direction.")
    print("  num_tiles_y: The number of tiles to create in the Y direction.")
    print("  tile_size: The size of each tile in pixels.")
    print("  folderName: The name of the folder to save the cropped tiles in.")

