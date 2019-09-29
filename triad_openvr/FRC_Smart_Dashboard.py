import threading
from networktables import NetworkTables
from VR_data import TrackerData
networktables_IP = "10.75.61.2"


cond = threading.Condition()
notified = [False]

def connection_listener(connected, info):
    print(info, '; Connected=%s' % connected)
    with cond:
        notified[0] = True
        cond.notify()


NetworkTables.initialize(server=networktables_IP)
try:
    NetworkTables.addconnection_listener(connection_listener, immediateNotify=True)
except:
    print("Unable to connect to network tables")

table = NetworkTables.getTable('SmartDashboard')



def publish_tracker_data(tracker, tracker_number):
    table.putNumber("x"+str(tracker_number), tracker.x)
    table.putNumber("y"+str(tracker_number), tracker.y)
    table.putNumber("z"+str(tracker_number), tracker.z)
    table.putNumber("x_rot"+str(tracker_number), tracker.x_rot)
    table.putNumber("y_rot"+str(tracker_number), tracker.y_rot)
    table.putNumber("z_rot"+str(tracker_number), tracker.z_rot)
#   print_data(x, y, z, x_rot, y_rot, z_rot)
def publish_other_data(fps, count):
    table.putNumber("count", count)
    table.putNumber("fps", fps)

def get_step():
    return table.getNumber("Step", -1)