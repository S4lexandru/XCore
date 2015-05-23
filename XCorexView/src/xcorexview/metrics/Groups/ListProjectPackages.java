package xcorexview.metrics.Groups;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;

import xmetamodel.XPackage;
import xmetamodel.XProject;
import xmetamodel.factory.FactoryMethod;

import com.salexandru.xcorex.interfaces.Group;
import com.salexandru.xcorex.interfaces.IGroupBuilder;
import com.salexandru.xcorex.metaAnnotation.GroupBuilder;

@GroupBuilder
public class ListProjectPackages implements IGroupBuilder<XPackage, XProject> {
	private void getPackages(final IPackageFragmentRoot rootFragment, final Group<XPackage> group_) {
		try {
			if (null == rootFragment || IPackageFragmentRoot.K_SOURCE != rootFragment.getKind()) {
				return;
			}
			
			for (final IJavaElement element: rootFragment.getChildren()) {
				if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
					getPackages((IPackageFragmentRoot)element, group_);
				}
				else if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
					getPackages((IPackageFragment)element, group_);
				}
			}
			
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getPackages(final IPackageFragment fragment, final Group<XPackage> group_) {
		try {
			if (null == fragment || IPackageFragmentRoot.K_SOURCE != fragment.getKind()) {
				return ;
			}
			
			group_.add(FactoryMethod.createXPackage(fragment));
			
			if (fragment.hasSubpackages()) {
				for (final IJavaElement element: fragment.getChildren()) {
					if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT) {
						getPackages((IPackageFragmentRoot)element, group_);
					}
					else if (element.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
						getPackages((IPackageFragment)element, group_);
					}
				}			
			}
			
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public Group<XPackage> buildGroup(XProject entity) {
		Group<XPackage> group_ = new Group<>();
		try {	
			for (final IPackageFragment fragment: entity.getUnderlyingObject().getPackageFragments()) {
				if (fragment.isDefaultPackage()) {
					continue;
				}
				getPackages(fragment, group_);
			}
		
		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return group_;
	}

}