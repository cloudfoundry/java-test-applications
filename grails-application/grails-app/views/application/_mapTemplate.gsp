<table>
<g:each var="key" in="${data.keySet()}">
	<tr>
		<td>${key}</td>
		<td>
		<g:set var="value" value="${data.get(key)}" />
		<g:if test="${value instanceof List || value?.class?.array}">
			<g:render template="listTemplate" model="[data: value]" />
		</g:if>
		<g:elseif test="${value instanceof Map}">
			<g:render template="mapTemplate" model="[data: value]" />
		</g:elseif>
		<g:elseif test="${value instanceof String}">
			${value}
		</g:elseif>
		<g:else>
			Unknown value type '${value?.getClass().simpleName}'
		</g:else>
		</td>
	</tr>
</g:each>
</table>
