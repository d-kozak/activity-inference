import datetime

import matplotlib.pyplot as plt

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
    grouped = {}
    for line in my_data:
        print(line)
        if not line[0] in grouped:
            grouped[line[0]] = []
        grouped[line[0]].append((line[2], line[1]))

    for (key, value) in grouped.items():
        dates = [line[0] for line in value]
        confidence = [line[1] for line in value]
        plt.plot(dates, confidence, label=key)
    plt.title('Activity inference')
    plt.legend()
    plt.show()


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
