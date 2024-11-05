<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="common" tagdir="/WEB-INF/tags/" %>
<!DOCTYPE html>
<html lang="ko">

<%-- <common:header /> --%>
<c:import url="/common/getHeader" />

<body>
    <div id="skipMenu">
        <a href="#main">본문 바로가기</a>
    </div>
    <main id="main">
        <div class="container">

			<c:import url="/common/getLeft" />

            <article>
				<jsp:doBody />
            </article>
        </div>
    </main>
</body>
</html>