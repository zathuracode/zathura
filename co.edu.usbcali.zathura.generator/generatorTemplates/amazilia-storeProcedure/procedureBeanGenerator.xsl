<?xml version='1.0' encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes" encoding="ISO-8859-1"/>
<xsl:output omit-xml-declaration="yes" />

<xsl:template match="/">
	<!-- Package declaration -->
	<xsl:text>package </xsl:text>
	<xsl:apply-templates select="class/packageName" />
	<xsl:text>; </xsl:text>
	
	<xsl:text>/**
	 * This document was Auto-generated
	 * @date Creation date: </xsl:text>
	 <xsl:apply-templates select="class/creationDate" />
	 <xsl:text>
	 * @author Zathura Code Generator http://code.google.com/p/zathura
	 */	
	</xsl:text>
	
	<!-- Class declaration -->
	<xsl:text>public class Bean</xsl:text>
	<xsl:apply-templates select="class/className" />
	<xsl:text>{ </xsl:text>
	
	<!-- Class Attributes -->
	<xsl:for-each select="class/attributes/attribute">					
		<xsl:text>private </xsl:text>
		<xsl:value-of select="type" />
		<xsl:text> </xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>; </xsl:text>			
	</xsl:for-each>

	<!-- Empty Constructor (Default)-->
	<xsl:text>/**Default Constructor (Empty)*/</xsl:text>
	<xsl:text>
	</xsl:text>
	<xsl:text>public Bean</xsl:text>
	<xsl:apply-templates select="class/className" />
	<xsl:text>(){</xsl:text>
	<xsl:text>super();</xsl:text>
	<xsl:text>}</xsl:text>

	<!-- Full Constructor -->
	<xsl:text>/**Full Constructor*/</xsl:text>
	<xsl:text>
	</xsl:text>
	<xsl:text>public Bean</xsl:text>
	<xsl:apply-templates select="class/className" />
	<xsl:text>(</xsl:text>
	<xsl:for-each select="class/attributes/attribute">
		<xsl:value-of select="type" />
		<xsl:text> </xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>,</xsl:text>		
	</xsl:for-each>
	<xsl:text>)</xsl:text>
	<xsl:text>{</xsl:text>
	<xsl:for-each select="class/attributes/attribute">
		<xsl:text>this.</xsl:text>
		<xsl:value-of select="name" />
		<xsl:text> = </xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>;</xsl:text>	
	</xsl:for-each>
	<xsl:text>}</xsl:text>			
	
	<!-- Getters and Setters -->	
	<xsl:for-each select="class/attributes/attribute">			
		<!-- Getters -->
		<xsl:text>public </xsl:text>
		<xsl:value-of select="type" />
		<xsl:text> get</xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>() {</xsl:text>			
		<xsl:text>return </xsl:text>
		<xsl:value-of select="name"/>
		<xsl:text>;</xsl:text>
		<xsl:text>}</xsl:text>
		<!-- Setters -->
		<xsl:text>public void </xsl:text>
		<xsl:text>set</xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>(</xsl:text>
		<xsl:value-of select="type" />					
		<xsl:text> </xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>) {</xsl:text>
		<xsl:text>this.</xsl:text>
		<xsl:value-of select="name" />
		<xsl:text> = </xsl:text>
		<xsl:value-of select="name" />
		<xsl:text>;</xsl:text>
		<xsl:text>}</xsl:text>
	</xsl:for-each>	
	<xsl:text>} </xsl:text>
</xsl:template>
</xsl:stylesheet>