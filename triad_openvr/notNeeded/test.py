import triad_openvr
import time
import sys
import threading
import os

from networktables import NetworkTables

cond = threading.Condition()
notified = [False]

def connectionListener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()

NetworkTables.initialize(server='192.168.0.35')
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
    
if interval:
    count = 1
    start_time = time.time()

    while(True):
        start = time.time()
        txt = ""
        x = round(v.devices["tracker_1"].get_pose_euler()[0],2)
        y = round(v.devices["tracker_1"].get_pose_euler()[1],2)
        z = round(v.devices["tracker_1"].get_pose_euler()[2],2)
        x_rot = round(v.devices["tracker_1"].get_pose_euler()[3],2)
        y_rot = round(v.devices["tracker_1"].get_pose_euler()[4],2)
        z_rot = round(v.devices["tracker_1"].get_pose_euler()[5],2)

        table.putNumber("x", x)
        table.putNumber("y", y)
        table.putNumber("z", z)
        table.putNumber("x_rot", x_rot)
        table.putNumber("y_rot", y_rot)
        table.putNumber("z_rot", z_rot)
        table.putNumber("count", count)
        table.putNumber("time", time.time()-start_time)
        table.putNumber("fps", count/(time.time()-start_time+0.000000001))
        print("X = ", x)
        print("Y = ", y)
        print("Z = ", z)
        print("X_Rot = ", x_rot)
        print("Y_Rot = ", y_rot)
        print("Z_Rot = ", z_rot)
        os.system('CLS')

        count += 1
        if (count > 100000):
                count = 0
        sleep_time = interval-(time.time()-start)
        if sleep_time>0:
            time.sleep(sleep_time)