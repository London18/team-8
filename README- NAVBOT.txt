navBotUI.html:
- developed in order to produce the layout of the chatbox system
- The layout is split into three segments: the top is the chat bar that reads 'NavBot' , the middle is where the conversation takes place between the user and Macs
and the bottom is the space left for the user to input text
- If user recieve three sets of articles from chatbox and does not feel satisfies, the user is referred to a real person to talk with

styles.css:
- Contains information about the style of the chatbox including fonts, colours, field sizes and spacing 

InterceptHTTP.java:
- automatic pop up detects when user is not interested in article by looking at scrolling and length of stay on a specific part of page
- developed to produce a reply to the user
- Infomation input by the user gets transferred to java
- This java code looks at the keywords in the user input & counts how many times the keywords related to a specific category are mentioned. The category with the 
most number of keywords mentioned will be the topic of the article/forum linked to the user in the chatbox.
- if the Chatbox is unable to produce a helpful response it will output an (external) number/link for the user to use
