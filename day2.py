import argparse
import pprint
import logging

FORMAT = '%(asctime)s - %(name)s - %(levelname)s - %(message)s'
logging.basicConfig(format=FORMAT)

TEST_PASSWORD = "1-3 b: cdefg"

def parse_password_components(password_components_raw):
	password_components = {}
	password_components_raw_split = password_components_raw.split()


	# Parse repeated spec
	repeated_spec = password_components_raw_split[0].split("-")
	password_components["lower_bound"] = int(repeated_spec[0])
	password_components["upper_bound"] = int(repeated_spec[1])

	# assume that its a single char plus ':' so we extract 'a' from 'a:'
	password_components["letter"] = password_components_raw_split[1][0] 
	
	# Third component is always just the password
	password_components["password"] = password_components_raw_split[2]

	return password_components

def is_password_valid(password_components):
	letter = password_components["letter"]
	upper_bound = password_components["upper_bound"]
	lower_bound = password_components["lower_bound"]
	password = password_components["password"]

	repeated_occurences = password.count(letter)
	return (repeated_occurences >= lower_bound and repeated_occurences <= upper_bound)



def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input_filename")

	args = parser.parse_args()

	print("Examining {0}".format(args.input_filename))

	# test_password_components = parse_password_components(TEST_PASSWORD)
	# print(is_password_valid(test_password_components))



if __name__ == "__main__":
	main()
