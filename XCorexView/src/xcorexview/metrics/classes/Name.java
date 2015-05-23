package xcorexview.metrics.classes;

import xmetamodel.XClass;

import com.salexandru.xcorex.interfaces.IPropertyComputer;
import com.salexandru.xcorex.metaAnnotation.PropertyComputer;

@PropertyComputer
public class Name implements IPropertyComputer<String, XClass> {
	@Override
	public String compute(XClass entity) {
		return entity.getUnderlyingObject().getElementName();
	}
}