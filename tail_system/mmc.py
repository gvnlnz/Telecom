import random
import queue
import threading
import time
import pygame

class MMcQueueSystem:
    def __init__(self, c, duration):
        self.c = c  # Number of servers
        self.queue = queue.Queue()
        self.servers = [None] * c
        self.lock = threading.Lock()
        self.duration = duration
        self.stop_event = threading.Event()
        self.total_waiting_time = 0
        self.total_system_time = 0
        self.total_packets = 0
        self.total_queue_packets = 0

    def arrival(self):
        while not self.stop_event.is_set():
            # Random arrival rate lambda
            lambda_rate = random.expovariate(6.0)
            time.sleep(lambda_rate)
            with self.lock:
                arrival_time = time.time()
                self.queue.put(arrival_time)
                self.total_queue_packets += 1
                print(f"Packet arrived at {arrival_time}")

    def departure(self, server_id):
        while not self.stop_event.is_set():
            # Random service rate mu
            mu_rate = random.expovariate(4.0)
            time.sleep(mu_rate)
            with self.lock:
                if not self.queue.empty():
                    arrival_time = self.queue.get()
                    departure_time = time.time()
                    waiting_time = departure_time - arrival_time
                    self.total_waiting_time += waiting_time
                    self.total_system_time += waiting_time
                    self.total_packets += 1
                    self.total_queue_packets -= 1
                    print(f"\033[92mPacket served by server {server_id} at {departure_time}\033[0m")
                    print(f"\033[94mTime in queue: {waiting_time}\033[0m")

    def start(self):
        arrival_thread = threading.Thread(target=self.arrival)
        arrival_thread.start()

        for i in range(self.c):
            departure_thread = threading.Thread(target=self.departure, args=(i,))
            departure_thread.start()

        # Run the simulation for the specified duration
        time.sleep(self.duration)
        self.stop_event.set()

        # Wait for threads to finish
        arrival_thread.join()
        for i in range(self.c):
            departure_thread.join()

        # Calculate and print metrics
        if self.total_packets > 0:
            Ws = self.total_system_time / self.total_packets
            Wq = self.total_waiting_time / self.total_packets
            Ls = self.total_packets / self.duration
            Lq = self.total_queue_packets / self.duration
            print(f"\nAverage time in system (Ws): {Ws:.2f} seconds")
            print(f"Average waiting time in queue (Wq): {Wq:.2f} seconds")
            print(f"Average number of packets in system (Ls): {Ls:.2f}")
            print(f"Average number of packets in queue (Lq): {Lq:.2f}")
        else:
            print("No packets were processed during the simulation.")

if __name__ == "__main__":
    c = 2  # Number of servers
    duration = int(input("Enter the duration of the simulation in seconds: "))
    mmc_system = MMcQueueSystem(c, duration)
    mmc_system.start()
