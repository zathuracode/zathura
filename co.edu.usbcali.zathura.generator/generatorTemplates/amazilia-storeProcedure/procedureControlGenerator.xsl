<?xml version='1.0' encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" indent="yes" encoding="ISO-8859-1"/>
<xsl:output omit-xml-declaration="yes" />

<xsl:template match="/">
	<!-- Package declaration -->
	<xsl:text>package </xsl:text>
	<xsl:apply-templates select="class/packageName" />
	<xsl:text>; </xsl:text>
	<!-- Import declaration -->
	<xsl:text>import java.util.List; </xsl:text>
	<xsl:text>import org.hibernate.HibernateException; </xsl:text>
	<xsl:text>import org.hibernate.Query; </xsl:text>
	<xsl:text>import org.hibernate.Session; </xsl:text>	
	<xsl:text>import net.geniar.utilities.HibernateSessionFactory;</xsl:text>	
	
	<xsl:text>/**
	 * This document was Auto-generated
	 * @date Creation date: </xsl:text>
	 <xsl:apply-templates select="class/creationDate" />
	 <xsl:text>
	 * @author Zathura Code Generator http://code.google.com/p/zathura
	 */	
	</xsl:text>
	
	<!-- Class declaration -->
	<xsl:text>public class Ctrl</xsl:text>
	<xsl:apply-templates select="class/className" />
	<xsl:text>{ </xsl:text>
	
	<!-- Empty Constructor (Default)-->
	<xsl:text>/**Default Constructor*/</xsl:text>
	<xsl:text>public Ctrl</xsl:text>
	<xsl:apply-templates select="class/className" />
	<xsl:text>(){</xsl:text>
	<xsl:text>super();</xsl:text>
	<xsl:text>}</xsl:text>
	
	<!-- Empty callProcedure method -->
	<xsl:text disable-output-escaping="yes">public static List&lt;Object[]&gt; callProcedure(){</xsl:text>	
	<xsl:text>Session session=HibernateSessionFactory.getSession();</xsl:text>
	<xsl:text disable-output-escaping="yes">List&lt;Object[]&gt; elements = null;</xsl:text>
	<xsl:text>try {</xsl:text>
	<xsl:text>Query query=session.getNamedQuery("</xsl:text>	
	<xsl:apply-templates select="class/procedureName" />
	<xsl:text>");</xsl:text>
	<xsl:for-each select="class/setQuerys/setQuery">
	<xsl:text>query. </xsl:text>
	<xsl:value-of select="setAttribute" />
	<xsl:text>; </xsl:text>
	</xsl:for-each>
	<xsl:text>elements= query.list();</xsl:text>	
	<xsl:text>}</xsl:text>	
	<xsl:text>catch (HibernateException e) {</xsl:text>	
	<xsl:text>e.printStackTrace();</xsl:text>
	<xsl:text>}</xsl:text>
	<xsl:text>return elements;</xsl:text>	
	<xsl:text>}</xsl:text>

	<!-- Test method (public static void main(String args[]))-->
	<xsl:text disable-output-escaping="yes">public static void main(String args[])
	{
		List &lt;Object[]&gt; elements = callProcedure();
		for (Object[] objects : elements)
		{
		    for (int i = 0; i &lt; objects.length; i++)
		    {
				System.out.println(objects[i] + "\n");
		    }
		}		
	}
	</xsl:text>		
	<xsl:text>}</xsl:text>	
	
</xsl:template>
</xsl:stylesheet>