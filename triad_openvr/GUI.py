import pygame
import Colours
import Constants
from VR_data import TrackerData
import math

pygame.init()
pygame.font.init()
my_font = pygame.font.SysFont('Arial', 20)


gameDisplay = pygame.display.set_mode((Constants.display_width, Constants.display_height))
gameDisplay.fill(Colours.white)

robot_image_source = pygame.image.load(r'images/Robot_TopDown.png')
target_image_source = pygame.image.load(r'images/Arrow.png')
destination_image_source = pygame.image.load(r'images/Destination.png')
cargoShipFront_Image = pygame.transform.rotozoom(pygame.image.load(r'images/CargoShipFront.png'), 90, 0.2)
rocket_Image = pygame.transform.rotozoom(pygame.image.load(r'images/Rocket.png'), -90, 0.2)
loadingStation_Image = pygame.transform.rotozoom(pygame.image.load(r'images/LoadingStation.png'), 0, 0.2)

def init():
    pygame.display.set_caption('Lighthouse tracking demo')
    gameDisplay.set_alpha(None)

def draw_data(tracker1, tracker2, trackerTarget, trackerDestination, fps, step):
    gameDisplay.fill(Colours.white)
    text_fps = my_font.render('FPS: ' + str(fps), True, Colours.red)
    text_step = my_font.render('Step: ' + str(step), True, Colours.red)
    text_rect_fps = text_fps.get_rect()
    text_rect_step = text_step.get_rect()
    height = 100
    width = 100
    draw_tracker_data(tracker1, 1, height, width)
    draw_tracker_data(tracker2, 2, height, width+400)
    #draw_tracker_data(trackerTarget, 0, height+400, width)
    #draw_tracker_data(trackerDestination, 0, height+500, width)
    text_rect_fps.center = (850, 780)
    text_rect_step.center = (950, 780)
    gameDisplay.blit(text_fps, text_rect_fps)
    gameDisplay.blit(text_step, text_rect_step)
def draw_tracker_data(tracker, tracker_no, height, width):
    width_gap = 30
    text_x = my_font.render('X:' + str(round(tracker.x, 4)), True, Colours.red)
    text_y = my_font.render('Y:' + str(round(tracker.y, 4)), True, Colours.green)
    text_z = my_font.render('Z:' + str(round(tracker.z, 4)), True, Colours.blue)
    text_rot_x = my_font.render('Rotate X:' + str(round(tracker.x_rot, 3)), True, Colours.red)
    text_rot_y = my_font.render('Rotate Y:' + str(round(tracker.y_rot, 3)), True, Colours.green)
    text_rot_z = my_font.render('Rotate Z:' + str(round(tracker.z_rot, 3)), True, Colours.blue)
    text_r = my_font.render('Quat R:' + str(round(tracker.r, 3)), True, Colours.red)
    text_i = my_font.render('Quat I:' + str(round(tracker.i, 3)), True, Colours.green)
    text_j = my_font.render('Quat J:' + str(round(tracker.j, 3)), True, Colours.blue)
    text_k = my_font.render('Quat K:' + str(round(tracker.k, 3)), True, Colours.blue)

    text_rect_x = text_x.get_rect()
    text_rect_y = text_y.get_rect()
    text_rect_z = text_z.get_rect()
    text_rect_rot_x = text_rot_x.get_rect()
    text_rect_rot_y = text_rot_y.get_rect()
    text_rect_rot_z = text_rot_z.get_rect()
    text_rect_r = text_r.get_rect()
    text_rect_i = text_i.get_rect()
    text_rect_j = text_j.get_rect()
    text_rect_k = text_k.get_rect()

    text_rect_x.center = (height, width)
    text_rect_y.center = (height, width + 1 * width_gap)
    text_rect_z.center = (height, width + 2 * width_gap)
    text_rect_rot_x.center = (height, width + 3 * width_gap)
    text_rect_rot_y.center = (height, width + 4 * width_gap)
    text_rect_rot_z.center = (height, width + 5 * width_gap)
    text_rect_r.center = (height, width + 6 * width_gap)
    text_rect_i.center = (height, width + 7 * width_gap)
    text_rect_j.center = (height, width + 8 * width_gap)
    text_rect_k.center = (height, width + 9 * width_gap)

    gameDisplay.blit(text_x, text_rect_x)
    gameDisplay.blit(text_y, text_rect_y)
    gameDisplay.blit(text_z, text_rect_z)
    gameDisplay.blit(text_rot_x, text_rect_rot_x)
    gameDisplay.blit(text_rot_y, text_rect_rot_y)
    gameDisplay.blit(text_rot_z, text_rect_rot_z)
    gameDisplay.blit(text_r, text_rect_r)
    gameDisplay.blit(text_i, text_rect_i)
    gameDisplay.blit(text_j, text_rect_j)
    gameDisplay.blit(text_k, text_rect_k)

def draw_image(image, point):
    width, height = image.get_size()
    gameDisplay.blit(image, (point[0]-width/2, point[1]-height/2))

def draw_objects(tracker1, tracker2, trackerTarget, trackerDestination):
    angle_offset = -90
    robot_Image = pygame.transform.rotozoom(robot_image_source, -tracker1.y_rot-angle_offset, 0.3)
    target_Image = pygame.transform.rotozoom(target_image_source, trackerTarget.y_rot-angle_offset+90, 0.1)
    destination_Image = pygame.transform.rotozoom(destination_image_source, trackerDestination.y_rot-angle_offset-180, 0.1)
    #y ranges from 2.5 to - 2.2
    #x ranges from 2 to - 2
    draw_image(robot_Image, (position_to_pixels(tracker1)))
    draw_image(target_Image, (position_to_pixels(trackerTarget)))
    draw_image(destination_Image, (position_to_pixels(trackerDestination)))
    #gameDisplay.blit(target_Image, (position_to_pixels(trackerTarget)))
    #gameDisplay.blit(robot_Image, (position_to_pixels(tracker2)))
    #gameDisplay.blit(destination_Image, (position_to_pixels(trackerDestination)))
    #gameDisplay.blit(cargoShipFront_Image, (600,200))
    #gameDisplay.blit(rocket_Image, (600,100))
    gameDisplay.blit(loadingStation_Image, (0,500))

def position_to_pixels(tracker):
    x = tracker.x - 0
    y = tracker.z + 0
    x, y = rotate((x, y), (0, 0), 70)
    #print(x, y)
    #x, y = tracker.x, tracker.y
    x_pos = (1.2+x)/3.2*Constants.display_width
    y_pos = (2.2+y)/(2.5+2.2)*Constants.display_height
    return x_pos, y_pos
def rotate(origin, point, angle):
    ox, oy = origin
    px, py = point
    angle = math.radians(angle)
    qx = ox + math.cos(angle) * (px - ox) - math.sin(angle) * (py - oy)
    qy = oy + math.sin(angle) * (px - ox) + math.cos(angle) * (py - oy)
    return qx, qy