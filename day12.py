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


def simulate_move_sequence(initial_position_and_bearing, moves):
	logging.info("Initial position {0}".format(initial_position_and_bearing))
	current_position_and_bearing = initial_position_and_bearing
	for move in moves:
		current_position_and_bearing = simulate_move(current_position_and_bearing, move)

	return current_position_and_bearing

def simulate_move(current_position_and_bearing, move):		
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

	logging.info("Applying {0}, now at {1}".format(move, current_position_and_bearing))

	return current_position_and_bearing

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

	moves = parse_all_moves_from_file(args.input_filename)
	final_pos = simulate_move_sequence(INITIAL_POSITION_AND_BEARING, moves)
	manhattan_dist = calculate_manhattan_distnace(final_pos)

	logging.info("Final pos is {0}. Manhattan distance is {1}".format(final_pos, manhattan_dist))

if __name__ == "__main__":
	main()
