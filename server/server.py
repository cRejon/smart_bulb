from bottle import run, request, get, post
from sklearn.neural_network import MLPClassifier
import pickle

@post('/entrenamiento')
def train():

    data = dict(request.json)
    # convert RGB string ("R_G_B") in arrays
    X = []
    for key in list(data.keys()):
        RGB_string = key.split('_')
        RGB = [int(i) for i in RGB_string]
        X.append(RGB)

    Y = list(data.values())
    # print for debugging 
    print(X)
    print (Y)
    # create the model
    model = MLPClassifier(solver = 'lbfgs',random_state=1).fit(X, Y)
    # save model in disk
    filename = 'model.sav'
    pickle.dump(model, open(filename, 'wb'))
    

@get('/prediccion')
def predict():
    # obtain the RGB values from the URL
    red = int(request.query.red)
    green = int(request.query.green)
    blue = int(request.query.blue)
    # load the model
    filename = 'model.sav'
    model = pickle.load(open(filename, 'rb'))
    # predict the outcome
    result = model.predict([[red, green, blue]])[0]

    return str(result)


run(host='localhost', port=8080)
