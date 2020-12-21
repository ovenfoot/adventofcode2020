import argparse
import pprint
import logging
import sys

FORMAT = '[%(asctime)s] [%(levelname)s] %(message)s'
logging.basicConfig(format=FORMAT, stream=sys.stdout, level=logging.DEBUG)



def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input_filename")

	args = parser.parse_args()

	logging.info("Examining file {0}".format(args.input_filename))

if __name__ == "__main__":
	main()
