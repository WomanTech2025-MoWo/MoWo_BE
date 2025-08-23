import pickle
import sys
import json
import numpy as np
import pandas as pd
import os
from sklearn.neighbors import KNeighborsClassifier

# =========================================================
# 1️⃣ CSV 로드
# =========================================================
csv_path = os.path.join(os.path.dirname(__file__), 'transposed_data.csv')
try:
    df = pd.read_csv(csv_path, index_col='abc')
except FileNotFoundError:
    print("오류: 'transposed_data.csv' 파일이 없습니다.")
    sys.exit(1)

# NaN 처리 (임시) → 모두 0으로 채움
df = df.fillna(0)

X_train = df.values.T  # 컬럼 방향 맞춤
# =========================================================
# ⚠️ 더미 레이블 생성 (임시용)
# =========================================================
y_train = ['A'] * X_train.shape[0]  # 모든 샘플 같은 레이블

model = KNeighborsClassifier(n_neighbors=1)
model.fit(X_train, y_train)

# =========================================================
# 1️⃣ 학습된 모델 로드
# =========================================================
# try:
#     with open("ai/model.pkl", "rb") as f:
#         model = pickle.load(f)
# except FileNotFoundError:
#     print("오류: 'ai/model.pkl' 파일이 없습니다. 모델 파일 경로를 확인하세요.")
#     sys.exit(1)

# =========================================================
# 2️⃣ 입력(JSON) 받기
# 예시 JSON 형식:
# {"features": [[0,1,1,0,1,0,1,1,0,0], [1,0,0,1,0,1,0,0,1,0]]}
# =========================================================
input_data = sys.stdin.read()

try:
    data = json.loads(input_data)
    X = np.array(data["features"])
except (json.JSONDecodeError, KeyError):
    print("오류: 올바른 JSON 형식으로 'features' 키를 포함해야 합니다.")
    sys.exit(1)

# =========================================================
# 3️⃣ 예측 수행
# =========================================================
try:
    preds = model.predict(X).tolist()
except Exception as e:
    print(f"예측 중 오류 발생: {e}")
    sys.exit(1)

# =========================================================
# 4️⃣ 결과를 JSON으로 반환
# =========================================================
print(json.dumps({"predictions": preds}))
