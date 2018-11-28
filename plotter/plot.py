import datetime

activities = {
    0: 'in_vehicle',
    1: 'on_bicycle',
    2: 'on_foot',
    3: 'still',
    4: 'unknown',
    5: 'tilting',
    7: 'walking',
    8: 'running'
}


def main():
    my_data = load_data("input.csv")
    for line in my_data:
        print(line)


def load_data(file_name):
    with open(file_name) as f:
        content = f.read()
        parsed = [line.split(",") for line in content.split("\n")]
        return [(get_activity_name(int(line[0])), int(line[1]), parse_date(line[2])) for line in parsed]


def parse_date(serialized_date):
    # remove the millis
    serialized_date = serialized_date.split(".")[0]
    return datetime.datetime.strptime(serialized_date, "%Y-%m-%d %H:%M:%S")


def get_activity_name(type):
    return activities[type]


if __name__ == '__main__':
    main()
