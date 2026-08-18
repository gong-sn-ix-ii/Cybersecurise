# import joblib

# import pythainlp
# from pythainlp.tokenize import word_tokenize
# def preprocess_text(text):
#     tokens = word_tokenize(text, engine='newmm')
#     return ' '.join(tokens)
#
# def classify_message(message):
#     processed_message = preprocess_text(message)
#     prediction = svm_model.predict([processed_message])
#     return prediction[0]
#
# svm_model = joblib.load('thai_sms_filter_model.pkl')
#
#
# message = "Example message to classify"
# prediction = classify_message(message)
# print(f"Predicted label: {prediction}")
