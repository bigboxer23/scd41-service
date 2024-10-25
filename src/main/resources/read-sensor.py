import time
import board
import adafruit_scd4x

i2c = board.I2C()
scd4x = adafruit_scd4x.SCD4X(i2c)
scd4x.start_periodic_measurement()

while True:
    if scd4x.data_ready:
        print("{0:d}:{1:0.1f}:{2:0.1f}".format(
            scd4x.CO2,
            scd4x.temperature,
            scd4x.relative_humidity))
    time.sleep(1)