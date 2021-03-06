package xcorexview.metrics.classes;

import com.salexandru.xcore.interfaces.IPropertyComputer;
import com.salexandru.xcore.metaAnnotation.PropertyComputer;

import exampletool.metamodel.entity.XClass;

@PropertyComputer
public class ToString implements IPropertyComputer<String, XClass> {
	@Override
	public String compute(XClass entity) {
		return entity.getUnderlyingObject().getElementName();
	}
}
