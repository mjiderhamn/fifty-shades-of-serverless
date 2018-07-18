<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <Order>
      <xsl:attribute name="customerId">
        <xsl:value-of select="order/head/customer/id" />
      </xsl:attribute>
      <xsl:for-each select="order/lines/line">
        <OrderLine>
          <xsl:attribute name="ArticleNo">
            <xsl:value-of select="article/id" />
          </xsl:attribute>
          <xsl:attribute name="Quantity">
            <xsl:value-of select="quantity" />
          </xsl:attribute>
        </OrderLine>
      </xsl:for-each>
    </Order>
  </xsl:template>
</xsl:stylesheet>