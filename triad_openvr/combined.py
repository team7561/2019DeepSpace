import triad_openvr
import time
import sys
import threading
import os
import pygame

from networktables import NetworkTables

class Coordinates:
    def __init__(self, x, y, z, x_rot, y_rot, z_rot):
        self.x = x
        self.y = y
        self.z = z


pygame.init()
pygame.font.init()

display_width = 800
display_height = 600

gameDisplay = pygame.display.set_mode((display_width, display_height))
pygame.display.set_caption('Lighthouse tracking demo')

black = (0, 0, 0)
white = (255, 255, 255)

clock = pygame.time.Clock()
crashed = False

def process_data(cur_time, fps):
    x = round(v.devices["tracker_1"].get_pose_euler()[0], 2)
    y = round(v.devices["tracker_1"].get_pose_euler()[1], 2)
    z = round(v.devices["tracker_1"].get_pose_euler()[2], 2)
    x_rot = round(v.devices["tracker_1"].get_pose_euler()[3], 2)
    y_rot = round(v.devices["tracker_1"].get_pose_euler()[4], 2)
    z_rot = round(v.devices["tracker_1"].get_pose_euler()[5], 2)
    table.putNumber("x", x)
    table.putNumber("y", y)
    table.putNumber("z", z)
    table.putNumber("x_rot", x_rot)
    table.putNumber("y_rot", y_rot)
    table.putNumber("z_rot", z_rot)
    table.putNumber("count", count)
    table.putNumber("time", cur_time)
    table.putNumber("fps", fps)
    #print_data(x, y, z, x_rot, y_rot, z_rot)
    return x, y, z, x_rot, y_rot, z_rot


def print_data(x, y, z, x_rot, y_rot, z_rot):
    print("X = ", x)
    print("Y = ", y)
    print("Z = ", z)
    print("X_Rot = ", x_rot)
    print("Y_Rot = ", y_rot)
    print("Z_Rot = ", z_rot)


cond = threading.Condition()
notified = [False]

def connectionListener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

NetworkTables.initialize(server='10.75.61.2')
NetworkTables.addConnectionListener(connectionListener, immediateNotify=True)

#with cond:
#    print("Waiting")
#    if not notified[0]:
#        cond.wait()

print("Connected")
table = NetworkTables.getTable('SmartDashboard')

v = triad_openvr.triad_openvr()
v.print_discovered_objects()

if len(sys.argv) == 1:
    interval = 1/250
elif len(sys.argv) == 2:
    interval = 1/float(sys.argv[1])
else:
    print("Invalid number of arguments")
    interval = False
    
while not crashed:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            crashed = True
    count = 1
    start = time.time()
    txt = ""

    cur_time = pygame.time.get_ticks()
    fps = pygame.time.Clock().get_fps()
    x, y, z, x_rot, y_rot, z_rot = process_data(cur_time, fps)
    count += 1
    if (count > 100000):
        count = 0

    gameDisplay.fill(white)
    myfont = pygame.font.SysFont('Arial', 30)
    text_x = myfont.render('X:' + str(x), True, [255, 0, 0])
    text_y = myfont.render('Y:' + str(y), True, [0, 255, 0])
    text_z = myfont.render('Z:' + str(z), True, [0, 0, 255])
    text_rot_x = myfont.render('Rotate X:' + str(x_rot), True, [255, 0, 0])
    text_rot_y = myfont.render('Rotate Y:' + str(y_rot), True, [0, 255, 0])
    text_rot_z = myfont.render('Rotate Z:' + str(z_rot), True, [0, 0, 255])
    textRect_x = text_x.get_rect()
    textRect_y = text_y.get_rect()
    textRect_z = text_z.get_rect()
    textRect_rot_x = text_rot_x.get_rect()
    textRect_rot_y = text_rot_y.get_rect()
    textRect_rot_z = text_rot_z.get_rect()
    height = 100
    height_gap = 50
    width = 100
    width_gap = 50
    textRect_x.center = (height, width)
    textRect_y.center = (height + 0*height_gap, width+1*width_gap)
    textRect_z.center = (height + 0*height_gap, width+2*width_gap)
    textRect_rot_x.center = (height + 0*height_gap, width+3*width_gap)
    textRect_rot_y.center = (height + 0*height_gap, width+4*width_gap)
    textRect_rot_z.center = (height + 0*height_gap, width+5*width_gap)
    gameDisplay.blit(text_x, textRect_x)
    gameDisplay.blit(text_y, textRect_y)
    gameDisplay.blit(text_z, textRect_z)
    gameDisplay.blit(text_rot_x, textRect_rot_x)
    gameDisplay.blit(text_rot_y, textRect_rot_y)
    gameDisplay.blit(text_rot_z, textRect_rot_z)
    robot_x = (1+x)/2*display_width
    robot_y = (1+x)/2*display_height
    print("Robot x" + str(robot_x))
    rect = pygame.Rect(robot_x, robot_y, 30, 30)
    pygame.draw.rect(gameDisplay, black, rect, 2)
    #surf = pygame.Surface((rect.w, rect.h))
    #surf_rect = surf.get_rect()

    #surf = pygame.transform.rotate(surf, 100)
    #gameDisplay.blit(surf, surf_rect)

    pygame.display.update()
    clock.tick(60)

pygame.quit()
quit()