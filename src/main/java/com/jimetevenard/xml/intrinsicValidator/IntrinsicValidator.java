package com.jimetevenard.xml.intrinsicValidator;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.validation.Validator;

import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.jimetevenard.xml.intrinsicValidator.utils.DraconianErrorHandler;

public class IntrinsicValidator extends Validator {
	
	private LSResourceResolver resourceResolver;
	private ErrorHandler errorHandler;
	
	

	protected IntrinsicValidator(LSResourceResolver resourceResolver, ErrorHandler errorHandler) {
		super();
		this.resourceResolver = resourceResolver;
		this.errorHandler = errorHandler;
	}

	@Override
	public void reset() {
		throw new RuntimeException("TODO"); // TODO
	}

	@Override
	public void validate(Source source, Result result) throws SAXException, IOException {
		
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setNamespaceAware(true);
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			
			SchemaDeclarationHandler declarationsHandler = new SchemaDeclarationHandler();
			
			
			reader.setErrorHandler(DraconianErrorHandler.INSTANCE);
			reader.setContentHandler(declarationsHandler);
			
			reader.parse(new InputSource(source.getSystemId())); // TODO really ?
			
			
			
		} catch (ParserConfigurationException e) {
			throw new SAXException(e);
		}
		
		
	}

	@Override
	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
		
	}

	@Override
	public ErrorHandler getErrorHandler() {
		return this.errorHandler;
	}

	@Override
	public void setResourceResolver(LSResourceResolver resourceResolver) {
		this.resourceResolver = resourceResolver;
		
	}

	@Override
	public LSResourceResolver getResourceResolver() {
		return this.resourceResolver;
	}

	

}