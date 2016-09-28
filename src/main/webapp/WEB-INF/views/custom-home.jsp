<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
  <head>
    <title>ok</title>
    <link rel="stylesheet" 
          type="text/css" 
          th:href="@{/resources/style.css}"/>
  </head>
  <body>
    <div id="header" th:include="page :: header"></div>
  
    <div id="content">
      <h1>amazing</h1>
  
      <form method="POST" enctype="multipart/form-data" action="eers/attachment">
        <input type="file" name="myfile">
        <input type="submit" value="Register" />
      </form>
  
      <img src="eers/attachment/2016-09-24-IMG_1463.JPG" width="500px" height="500px">
    </div>
  </body>
</html>
