curl -X POST https://api.openai.com/v1/chat/completions \
-H "Content-Type: application/json" \
-H "Authorization: Bearer API_KEY
-d '{
"model": "gpt-3.5-turbo",
"messages": [{"role": "user", "content": "Hello!"}]
}'


Set the environment variable with the chatGPT key:
CHATGPT_API_KEY=<key>

Address to access the h2-console:
http://localhost:8081/h2-console/