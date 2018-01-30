import csv, random, math

"""
A simple classifier base on the gaussian naive bayes and
problem of the pima indians diabetes.
(https://archive.ics.uci.edu/ml/datasets/Pima+Indians+Diabetes)
"""

def load_csv_file(filename):
    with open(filename) as f:
        lines = csv.reader(f)
        data_set = list(lines)
    for i in range(len(data_set)):
        data_set[i] = [float(x) for x in data_set[i]]
    return data_set

def split_data_set(data_set, split_ratio):
    train_size = int(len(data_set) * split_ratio)
    train_set = []
    data_set_copy = list(data_set)
    while len(train_set) < train_size:
        index = random.randrange(len(data_set_copy))
        train_set.append(data_set_copy.pop(index))
    return [train_set, data_set_copy]

def separate_by_class(data_set, class_index):
    result = {}
    for i in range(len(data_set)):
        vector = data_set[i]
        class_val = vector[class_index]
        if (class_val not in result):
            result[class_val] = []
        result[class_val].append(vector)
    return result

def mean(numbers):
    return sum(numbers) / float(len(numbers))

def stdev(numbers):
    avg = mean(numbers)
    variance = sum([pow(x - avg, 2) for x in numbers]) / float(len(numbers))
    return math.sqrt(variance)

def summarize(data_set):
    summaries = [(mean(feature), stdev(feature)) for feature in zip(*data_set)]
    del summaries[-1]
    return summaries

def summarize_by_class(data_set):
    class_map = separate_by_class(data_set, -1)
    summaries = {}
    for class_val, data in class_map.items():
        summaries[class_val] = summarize(data)
    return summaries

def calculate_probability(x, mean, stdev):
    exponent = math.exp(-(math.pow(x - mean, 2) / (2 * math.pow(stdev, 2))))
    return (1 / (math.sqrt(2 * math.pi) * stdev)) * exponent

def calculate_conditional_probabilities(summaries, input_vector):
    probabilities = {}
    for class_val, class_summaries in summaries.items():
        probabilities[class_val] = 1
        for i in range(len(class_summaries)):
            mean, stdev = class_summaries[i]
            x = input_vector[i]
            probabilities[class_val] *= calculate_probability(x, mean, stdev)
    return probabilities

def predict(summaries, input_vector):
    probabilities = calculate_conditional_probabilities(summaries, input_vector)
    best_label, best_prob = None, -1
    for class_val, probability in probabilities.items():
        if best_label is None or probability > best_prob:
            best_label = class_val
            best_prob = probability
    return best_label

def get_predictions(summaries, test_set):
    predictions = []
    for i in range(len(test_set)):
        result = predict(summaries, test_set[i])
        predictions.append(result)
    return predictions

def get_accuracy(predictions, test_set):
    correct = 0
    for x in range(len(test_set)):
        if test_set[x][-1] == predictions[x]:
            correct += 1
    return (correct / float(len(test_set))) * 100.0

def main():
    filename = 'pima-indians-diabetes.data.csv'
    split_ratio = 0.67
    data_set = load_csv_file(filename)
    train_set, test_set = split_data_set(data_set, split_ratio)
    print('Split %s rows into train set = %s and test set = %s rows'
                %(len(data_set), len(train_set), len(test_set)))
    # prepare model
    summaries = summarize_by_class(train_set)
    # predict and test
    predictions = get_predictions(summaries, test_set)
    accuracy = get_accuracy(predictions, test_set)
    print('Accuracy: %s' % accuracy)

main()
