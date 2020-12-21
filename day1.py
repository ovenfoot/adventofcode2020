import argparse


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input_filename")

	args = parser.parse_args()

	print(args.input_filename)

if __name__ == "__main__":
	main()
