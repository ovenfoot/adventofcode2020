from math import sqrt


DOOR_PUBLIC_KEY = 2069194
CARD_PUBLIC_KEY = 16426071

DIVISOR = 20201227
SUBJECT_NUMBER = 7

def get_one_step_forward(num, subject_number):
	return (num * subject_number) % DIVISOR

door_loop_size = 0
a = 1
while(a != DOOR_PUBLIC_KEY):
	door_loop_size += 1
	a = get_one_step_forward(a, SUBJECT_NUMBER)
	# print(i)

print("door_loop_size is {}".format(door_loop_size))

i = 0
encryption_key = 1
while(i < door_loop_size):
	i += 1
	encryption_key = get_one_step_forward(encryption_key, CARD_PUBLIC_KEY)

print("encryption_key is {}".format(encryption_key))
# a = DIVISOR + DOOR_PUBLIC_KEY
# print(get_one_step_before(a))
# i = 0
# while (a is not 1):
# 	i += 1
# 	a = get_one_step_before(a)
# 	print(i)



