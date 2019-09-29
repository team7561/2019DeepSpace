import pygame
import FRC_Smart_Dashboard
import GUI
import VR_data
from VR_data import TrackerData

clock = pygame.time.Clock()
crashed = False
tracker1 = TrackerData(0, 0, 0, 0, 0,0)
# x, y, z, x_rot, y_rot, z_rot = FRC_Smart_Dashboard.process_data(v, 0, 0)

<<<<<<< HEAD
def process_data(cur_time, fps):
    if (v.devices["tracker_1"].get_pose_euler() is None):
        return -99, -99, -99, -999, -999, -999
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
    
=======
>>>>>>> 278b73332c22dfd44caa295ffd3dfce7316f978b
while not crashed:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            crashed = True
    count = 1
    txt = ""

    cur_time = pygame.time.get_ticks()
    fps = clock.get_fps()
    print("FPS: " + str(fps))
    FRC_Smart_Dashboard.publish_tracker_data(VR_data.get_data(1), 1)
    FRC_Smart_Dashboard.publish_tracker_data(VR_data.get_data(2), 2)
    FRC_Smart_Dashboard.publish_other_data(fps, count)
    GUI.draw_data(tracker1)
    count += 1
    if count > 100000:
        count = 0
    GUI.draw_objects(tracker1)
    print(FRC_Smart_Dashboard.get_step())
    pygame.display.update()
    clock.tick()

pygame.quit()
quit()
