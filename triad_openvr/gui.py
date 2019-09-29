import pygame
import Colours
import Constants
from VR_data import TrackerData

pygame.init()
pygame.font.init()
my_font = pygame.font.SysFont('Arial', 30)


gameDisplay = pygame.display.set_mode((Constants.display_width, Constants.display_height))
gameDisplay.fill(Colours.white)

robot_image_source = pygame.image.load(r'images/Robot_TopDown.png')
cargoShipFront_Image = pygame.transform.rotozoom(pygame.image.load(r'images/CargoShipFront.png'), 0, 0.2)
rocket_Image = pygame.transform.rotozoom(pygame.image.load(r'images/Rocket.png'), -90, 0.2)
loadingStation_Image = pygame.transform.rotozoom(pygame.image.load(r'images/LoadingStation.png'), 90, 0.2)

def init():
    pygame.display.set_caption('Lighthouse tracking demo')
    gameDisplay.set_alpha(None)

def draw_data(tracker1, fps, step):
    gameDisplay.fill(Colours.white)
    text_fps = my_font.render('FPS: ' + str(fps), True, Colours.red)
    text_step = my_font.render('Step: ' + str(step), True, Colours.red)
    text_x = my_font.render('X:' + str(round(tracker1.x, 4)), True, Colours.red)
    text_y = my_font.render('Y:' + str(round(tracker1.y, 4)), True, Colours.green)
    text_z = my_font.render('Z:' + str(round(tracker1.z, 4)), True, Colours.blue)
    text_rot_x = my_font.render('Rotate X:' + str(round(tracker1.x_rot, 3)), True, Colours.red)
    text_rot_y = my_font.render('Rotate Y:' + str(round(tracker1.y_rot, 3)), True, Colours.green)
    text_rot_z = my_font.render('Rotate Z:' + str(round(tracker1.z_rot, 3)), True, Colours.blue)
    text_rect_fps = text_fps.get_rect()
    text_rect_step = text_step.get_rect()
    text_rect_x = text_x.get_rect()
    text_rect_y = text_y.get_rect()
    text_rect_z = text_z.get_rect()
    text_rect_rot_x = text_rot_x.get_rect()
    text_rect_rot_y = text_rot_y.get_rect()
    text_rect_rot_z = text_rot_z.get_rect()
    height = 100
    width = 100
    width_gap = 50
    text_rect_fps.center = (700, 500)
    text_rect_step.center = (400, 400)
    text_rect_x.center = (height, width)
    text_rect_y.center = (height, width + 1 * width_gap)
    text_rect_z.center = (height, width + 2 * width_gap)
    text_rect_rot_x.center = (height, width + 3 * width_gap)
    text_rect_rot_y.center = (height, width + 4 * width_gap)
    text_rect_rot_z.center = (height, width + 5 * width_gap)
    gameDisplay.blit(text_fps, text_rect_fps)
    gameDisplay.blit(text_step, text_rect_step)
    gameDisplay.blit(text_x, text_rect_x)
    gameDisplay.blit(text_y, text_rect_y)
    gameDisplay.blit(text_z, text_rect_z)
    gameDisplay.blit(text_rot_x, text_rect_rot_x)
    gameDisplay.blit(text_rot_y, text_rect_rot_y)
    gameDisplay.blit(text_rot_z, text_rect_rot_z)

def draw_objects(tracker1):
    robot_Image = pygame.transform.rotozoom(robot_image_source, tracker1.y_rot, 0.1)

    robot_x = (1+tracker1.x)/2*Constants.display_width
    robot_y = (1+tracker1.x)/2*Constants.display_height

    gameDisplay.blit(robot_Image, (robot_x, robot_y))
    gameDisplay.blit(cargoShipFront_Image, (600,200))
    gameDisplay.blit(rocket_Image, (600,100))
    gameDisplay.blit(loadingStation_Image, (450,450))

def position_to_pixels(tracker):
    x_pos = (1+tracker.x)/2*Constants.display_width
    y_pos = (1+tracker.y)/2*Constants.display_height
    return x_pos, y_pos