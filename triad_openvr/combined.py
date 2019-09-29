import pygame
import FRC_Smart_Dashboard
import GUI
import VR_data
from VR_data import TrackerData

clock = pygame.time.Clock()
crashed = False
tracker1 = TrackerData(0, 0, 0, 0, 0,0)
# x, y, z, x_rot, y_rot, z_rot = FRC_Smart_Dashboard.process_data(v, 0, 0)

while not crashed:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            crashed = True
    count = 1
    cur_time = pygame.time.get_ticks()
    fps = int(clock.get_fps())
    tracker1 = VR_data.get_data(1)
    FRC_Smart_Dashboard.publish_tracker_data(VR_data.get_data(1), 1)
    FRC_Smart_Dashboard.publish_tracker_data(VR_data.get_data(2), 2)
    FRC_Smart_Dashboard.publish_other_data(fps, count)
    GUI.draw_data(tracker1, fps, FRC_Smart_Dashboard.get_step())
    count += 1
    if count > 100000:
        count = 0
    GUI.draw_objects(tracker1)
    #print(FRC_Smart_Dashboard.get_step())
    pygame.display.update()
    clock.tick()

pygame.quit()
quit()