import argparse
import pprint
import logging
import sys

FORMAT = '[%(asctime)s] [%(levelname)s] %(message)s'
logging.basicConfig(format=FORMAT, stream=sys.stdout, level=logging.DEBUG)

TEST_PASSWORD = "1-3 b: cdefg"

def parse_password_components(password_components_raw):
	password_components = {}
	password_components_raw_split = password_components_raw.split()

	# Parse repeated spec
	repeated_spec = password_components_raw_split[0].split("-")
	password_components["position_one"] = int(repeated_spec[0])
	password_components["position_two"] = int(repeated_spec[1])

	# assume that its a single char plus ':' so we extract 'a' from 'a:'
	password_components["letter"] = password_components_raw_split[1][0] 
	
	# Third component is always just the password
	password_components["password"] = password_components_raw_split[2]

	return password_components

def is_password_valid(password_components):
	letter = password_components["letter"]
	position_two = password_components["position_two"]
	position_one = password_components["position_one"]
	password = password_components["password"]

	counter = 0

	if len(password) >= position_one:
		counter = counter + 1 if password[position_one - 1] == letter else counter

	if len(password) >= position_two:
		counter = counter + 1 if password[position_two - 1] == letter else counter

	return (counter == 1)


def count_valid_passwords(filename):
	valid_count = 0
	with open(filename, 'r') as file:
		for line in file:
			line = line.rstrip()
			logging.debug("Examinig {0}".format(line))
			password_components = parse_password_components(line)
			is_valid = is_password_valid(password_components)
			logging.info("\"{0}\" - valid? {1}".format(line, is_valid))
			if is_valid is True:
				valid_count = valid_count + 1

	logging.info("There are {0} valid passwords".format(valid_count))


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input_filename")

	args = parser.parse_args()

	logging.info("Examining file {0}".format(args.input_filename))

	count_valid_passwords(args.input_filename)

	# test_password_components = parse_password_components(TEST_PASSWORD)
	# print(is_password_valid(test_password_components))



if __name__ == "__main__":
	main()
