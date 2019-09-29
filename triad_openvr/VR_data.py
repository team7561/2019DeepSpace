import triad_openvr
import Constants
from math import sin, cos, pi

v = None
try:
    v = triad_openvr.triad_openvr()
    v.print_discovered_objects()
except:
    print("Error Encountered")


class TrackerData:
    def __init__(self, x = 0, y = 0, z = 0, x_rot = 0, y_rot = 0, z_rot = 0):
        self.x = x
        self.y = y
        self.z = z
        self.x_rot = x_rot
        self.y_rot = y_rot
        self.z_rot = z_rot

    def convert_to_pixels(self):
        pixel_x = (1 + self.x) / 2 * Constants.display_width
        pixel_y = (1 + self.x) / 2 * Constants.display_height
        return pixel_x, pixel_y


def get_data(tracker_number):
    tracker = TrackerData(0, 0, 0, 0, 0, 0)
    if v.devices["tracker_"+str(tracker_number)] is not None:
        sfrhgtdtrhd = v.devices["tracker_"+str(tracker_number)].get_pose_euler()
        if sfrhgtdtrhd is not None:
            tracker.x = round(sfrhgtdtrhd[0], 2)
            tracker.y = round(sfrhgtdtrhd[1], 2)
            tracker.z = round(sfrhgtdtrhd[2], 2)
            tracker.x_rot = round(sfrhgtdtrhd[3], 2)
            tracker.y_rot = round(sfrhgtdtrhd[4], 2)
            tracker.z_rot = round(sfrhgtdtrhd[5], 2)
            # Tracker behind robot lift
            offset = 0.3
            if tracker_number == 1:
                tracker.x += offset * sin(tracker.y_rot * pi / 180)
                tracker.z -= offset * cos(tracker.y_rot * pi/ 180)

            # Tracker on robot arm
            if tracker_number == 2:
                tracker.x += offset * sin(tracker.y_rot * pi/ 180)
                tracker.z -= offset * cos(tracker.y_rot * pi / 180)
    return tracker


def print_data(tracker):
    print("X = ", tracker.x)
    print("Y = ", tracker.y)
    print("Z = ", tracker.z)
    print("X_Rot = ", tracker.x_rot)
    print("Y_Rot = ", tracker.y_rot)
    print("Z_Rot = ", tracker.z_rot)
