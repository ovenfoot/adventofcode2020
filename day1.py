import argparse


def main():
	parser = argparse.ArgumentParser()
	parser.add_argument("input-filename")

	args = parser.parse_args()

	print(args.input_filename)
	print("i am doing things right now")

if __name__ == "main":
	print("sup")
	main()
