import argparse
import pprint
import logging
import sys

FORMAT = '[%(asctime)s] [%(levelname)s] %(message)s'
logging.basicConfig(format=FORMAT, stream=sys.stdout, level=logging.DEBUG)

RIGHT_TURN_SEQ = ["N", "E", "S", "W"]
LEFT_TURN_SEQ = ["N", "W", "S", "E"]

INITIAL_POSITION_AND_BEARING = {
	"bearing": "E",
	"n-pos" : 0,
	"e-pos" : 0
	}

INITIAL_WAYPOINT_POSITION_AND_BEARING = {
	"bearing": "E",
	"n-pos" : 1,
	"e-pos" : 10
	}

INITIAL_PART_2_POSITION_INFO = {
	"ferry": INITIAL_POSITION_AND_BEARING,
	"waypoint": INITIAL_WAYPOINT_POSITION_AND_BEARING
}


def simulate_move_sequence_part1(initial_position_and_bearing, moves):
	logging.info("Initial position {0}".format(initial_position_and_bearing))
	current_position_and_bearing = initial_position_and_bearing
	for move in moves:
		current_position_and_bearing = simulate_move_part1(current_position_and_bearing, move)

	return current_position_and_bearing

def simulate_move_part1(current_position_and_bearing, move):		
	action = move["action"]
	
	if move["action"] == "F":
		action = current_position_and_bearing["bearing"]

	if action == "N":
		current_position_and_bearing["n-pos"] = current_position_and_bearing["n-pos"] + move["value"]
	elif action == "S":
		current_position_and_bearing["n-pos"] = current_position_and_bearing["n-pos"] - move["value"]
	elif action == "E":
		current_position_and_bearing["e-pos"] = current_position_and_bearing["e-pos"] + move["value"]
	elif action == "W":
		current_position_and_bearing["e-pos"] = current_position_and_bearing["e-pos"] - move["value"]
	else:
		# turn left or right
		current_position_and_bearing["bearing"] = turn_ferry(current_position_and_bearing["bearing"], action, move["value"])

	logging.debug("[PART1] Applying {0}, now at {1}".format(move, current_position_and_bearing))

	return current_position_and_bearing


def simulate_move_sequence_part2(initial_posiiton_info, moves):
	logging.info("Initial position {0}".format(initial_posiiton_info))
	current_position_info = initial_posiiton_info
	for move in moves:
		current_position_info = simulate_move_part2(current_position_info, move)
	return current_position_info

def simulate_move_part2(current_position_info, move):
	current_position_and_bearing = current_position_info["ferry"]
	waypoint_position_and_bearing = current_position_info["waypoint"]
	
	if move["action"] == "F":
		# The return types here are fairly messy but i cbf
		position_info = move_ferry_toward_waypoint(current_position_and_bearing, waypoint_position_and_bearing, move["value"])
		current_position_and_bearing = position_info["ferry"]
		waypoint_position_and_bearing = position_info["waypoint"]
	elif move["action"] == "L" or move["action"] == "R":
	 	waypoint_position_and_bearing = turn_waypoint(current_position_info, move["action"], move["value"])
	else:
		# N, S, E, W actions are all the same as part 1, they just move the waypoint
		waypoint_position_and_bearing = simulate_move_part1(waypoint_position_and_bearing, move) 

	position_info = {"ferry": current_position_and_bearing, "waypoint": waypoint_position_and_bearing}

	logging.info("[PART2] Applying {0}, now at {1}".format(move, pprint.pformat(position_info)))

	return position_info

def move_ferry_toward_waypoint(current_position_and_bearing, waypoint_position_and_bearing, value):
	vertical_move = {}
	horizontal_move = {}

	# Calculate relative position
	horizontal_displacement = waypoint_position_and_bearing["e-pos"] - current_position_and_bearing["e-pos"]
	horizontal_dir = "E" if horizontal_displacement > 0 else "W" 
	horizontal_move = {"action": horizontal_dir, "value": value * abs(horizontal_displacement)}

	vertical_displacement = waypoint_position_and_bearing["n-pos"] - current_position_and_bearing["n-pos"]
	vertical_dir = "N" if vertical_displacement > 0 else "S"
	vertical_move = {"action": vertical_dir, "value": value * abs(vertical_displacement)}

	waypoint_position_and_bearing = simulate_move_part1(waypoint_position_and_bearing, horizontal_move)
	waypoint_position_and_bearing = simulate_move_part1(waypoint_position_and_bearing, vertical_move)

	current_position_and_bearing = simulate_move_part1(current_position_and_bearing, horizontal_move)
	current_position_and_bearing = simulate_move_part1(current_position_and_bearing, vertical_move)

	return {"ferry": current_position_and_bearing, "waypoint": waypoint_position_and_bearing}

def turn_waypoint(position_info, direction, degrees):
	current_position_and_bearing = position_info["ferry"]
	waypoint_position_and_bearing = position_info["waypoint"]

	horizontal_displacement = waypoint_position_and_bearing["e-pos"] - current_position_and_bearing["e-pos"]
	vertical_displacement = waypoint_position_and_bearing["n-pos"] - current_position_and_bearing["n-pos"]


	num_turns = int(degrees/90) % 4

	# Note: there are only 3 positions distinct from the starting position
	# We only need 3 cases 
	if num_turns == 2:
		horizontal_displacement =  - horizontal_displacement
		vertical_displacement =  - vertical_displacement
	elif (num_turns == 1 and direction == "R") or (num_turns == 3 and direction == "L"):
		horizontal_displacement_tmp = horizontal_displacement
		horizontal_displacement = vertical_displacement
		vertical_displacement =  - horizontal_displacement_tmp
	elif (num_turns == 3 and direction == "R") or (num_turns == 1 and direction == "L"):
		horizontal_displacement_tmp = horizontal_displacement
		horizontal_displacement = - vertical_displacement
		vertical_displacement =  horizontal_displacement_tmp

	waypoint_position_and_bearing["e-pos"] = current_position_and_bearing["e-pos"] + horizontal_displacement
	waypoint_position_and_bearing["n-pos"] = current_position_and_bearing["n-pos"] + vertical_displacement

	return waypoint_position_and_bearing
	
def turn_ferry(current_bearing, direction, degrees):
	logging.debug("Turning {0} by {1} from {2}".format(direction, degrees, current_bearing))

	sequence = []
	if direction == "L":
		sequence = LEFT_TURN_SEQ
	elif direction == "R":
		sequence = RIGHT_TURN_SEQ

	current_index = find_current_bearing_in_sequence(current_bearing, sequence)
	num_turns = int(degrees/90)
	new_index = (current_index + num_turns) % 4

	return sequence[new_index]

def find_current_bearing_in_sequence(current_bearing, sequence):
	print(sequence)
	index = 0
	for bearing in sequence:
		if current_bearing == bearing:
			logging.debug("{0} is {1} position in sequence {2}".format(current_bearing, index, sequence))
			return index
		index = index + 1

def parse_single_move(move_raw_string):
	move = {}
	move["action"] = str(move_raw_string[0])
	move["value"] = int(move_raw_string[1:])
	logging.debug("Parsed out single move {0} from {1}".format(move, move_raw_string))
	return move

def parse_all_moves_from_file(filename):
	moves = []
	with open(filename, 'r') as file:
		for line in file:
			line = line.rstrip()
			moves.append(parse_single_move(line))
			
	logging.info("Parsed {0} moves".format(len(moves)))
	return moves

def calculate_manhattan_distnace(position_and_bearing):
	return abs(position_and_bearing["n-pos"]) + abs(position_and_bearing["e-pos"])


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input_filename")

	args = parser.parse_args()

	logging.info("Examining file {0}".format(args.input_filename))

	# moves = parse_all_moves_from_file(args.input_filename)
	
	# Part 1
	# final_pos = simulate_move_sequence_part1(INITIAL_POSITION_AND_BEARING, moves)

	# Part 2
	move1 = parse_single_move("F10")
	move2 = parse_single_move("N3")
	move3 = parse_single_move("F7")
	move4 = parse_single_move("R90")
	move5 = parse_single_move("F11")

	simulate_move_sequence_part2(INITIAL_PART_2_POSITION_INFO, [move1, move2, move3, move4, move5])
	# manhattan_dist = calculate_manhattan_distnace(final_pos)

	# logging.info("Final pos is {0}. Manhattan distance is {1}".format(final_pos, manhattan_dist))

if __name__ == "__main__":
	main()
