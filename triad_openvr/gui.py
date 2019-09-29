import pygame
import Colours
import Constants
from VR_data import TrackerData
import math

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

def draw_data(tracker1, tracker2, fps, step):
    gameDisplay.fill(Colours.white)
    text_fps = my_font.render('FPS: ' + str(fps), True, Colours.red)
    text_step = my_font.render('Step: ' + str(step), True, Colours.red)
    text_rect_fps = text_fps.get_rect()
    text_rect_step = text_step.get_rect()
    height = 100
    width = 100
    draw_tracker_data(tracker1, 1, height, width)
    draw_tracker_data(tracker2, 2, height, width+400)
    text_rect_fps.center = (700, 500)
    text_rect_step.center = (400, 400)
    gameDisplay.blit(text_fps, text_rect_fps)
    gameDisplay.blit(text_step, text_rect_step)
def draw_tracker_data(tracker, tracker_no, height, width):
    width_gap = 50
    text_x = my_font.render('X:' + str(round(tracker.x, 4)), True, Colours.red)
    text_y = my_font.render('Y:' + str(round(tracker.y, 4)), True, Colours.green)
    text_z = my_font.render('Z:' + str(round(tracker.z, 4)), True, Colours.blue)
    text_rot_x = my_font.render('Rotate X:' + str(round(tracker.x_rot, 3)), True, Colours.red)
    text_rot_y = my_font.render('Rotate Y:' + str(round(tracker.y_rot, 3)), True, Colours.green)
    text_rot_z = my_font.render('Rotate Z:' + str(round(tracker.z_rot, 3)), True, Colours.blue)
    text_rect_x = text_x.get_rect()
    text_rect_y = text_y.get_rect()
    text_rect_z = text_z.get_rect()
    text_rect_rot_x = text_rot_x.get_rect()
    text_rect_rot_y = text_rot_y.get_rect()
    text_rect_rot_z = text_rot_z.get_rect()
    text_rect_x.center = (height, width)
    text_rect_y.center = (height, width + 1 * width_gap)
    text_rect_z.center = (height, width + 2 * width_gap)
    text_rect_rot_x.center = (height, width + 3 * width_gap)
    text_rect_rot_y.center = (height, width + 4 * width_gap)
    text_rect_rot_z.center = (height, width + 5 * width_gap)
    gameDisplay.blit(text_x, text_rect_x)
    gameDisplay.blit(text_y, text_rect_y)
    gameDisplay.blit(text_z, text_rect_z)
    gameDisplay.blit(text_rot_x, text_rect_rot_x)
    gameDisplay.blit(text_rot_y, text_rect_rot_y)
    gameDisplay.blit(text_rot_z, text_rect_rot_z)


def draw_objects(tracker1):
    robot_Image = pygame.transform.rotozoom(robot_image_source, tracker1.y_rot, 0.1)
    gameDisplay.blit(robot_Image, (position_to_pixels(tracker1)))
    gameDisplay.blit(cargoShipFront_Image, (600,200))
    gameDisplay.blit(rocket_Image, (600,100))
    gameDisplay.blit(loadingStation_Image, (450,450))

def position_to_pixels(tracker):
    x = tracker.x - 0.3966
    y = tracker.z + 3.3549
    x, y = rotate((x, y), (0, 0), 110)
    print(x, y)
    #x, y = tracker.x, tracker.y
    x_pos = (1+x)/2*Constants.display_width
    y_pos = (1+y)/2*Constants.display_height
    return x_pos, y_pos
def rotate(origin, point, angle):
    ox, oy = origin
    px, py = point
    angle = math.radians(angle)
    qx = ox + math.cos(angle) * (px - ox) - math.sin(angle) * (py - oy)
    qy = oy + math.sin(angle) * (px - ox) + math.cos(angle) * (py - oy)
    return qx, qy