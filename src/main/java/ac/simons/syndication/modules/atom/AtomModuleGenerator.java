/**
 * Created by Michael Simons, michael-simons.eu
 * and released under The BSD License
 * http://www.opensource.org/licenses/bsd-license.php
 *
 * Copyright (c) 2011, Michael Simons
 * All rights reserved.
 *
 * Redistribution  and  use  in  source   and  binary  forms,  with  or   without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source   code must retain   the above copyright   notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary  form must reproduce  the above copyright  notice,
 *   this list of conditions  and the following  disclaimer in the  documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name  of  michael-simons.eu   nor the names  of its contributors
 *   may be used  to endorse   or promote  products derived  from  this  software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS  PROVIDED BY THE  COPYRIGHT HOLDERS AND  CONTRIBUTORS "AS IS"
 * AND ANY  EXPRESS OR  IMPLIED WARRANTIES,  INCLUDING, BUT  NOT LIMITED  TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  THE COPYRIGHT HOLDER OR CONTRIBUTORS  BE LIABLE
 * FOR ANY  DIRECT, INDIRECT,  INCIDENTAL, SPECIAL,  EXEMPLARY, OR  CONSEQUENTIAL
 * DAMAGES (INCLUDING,  BUT NOT  LIMITED TO,  PROCUREMENT OF  SUBSTITUTE GOODS OR
 * SERVICES; LOSS  OF USE,  DATA, OR  PROFITS; OR  BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT  LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE  USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package ac.simons.syndication.modules.atom;

import static com.rometools.utils.Strings.isBlank;

import java.util.Collections;
import java.util.Set;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;

import com.rometools.rome.feed.atom.Link;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.io.ModuleGenerator;

/**
 * The generator for {@link AtomModule}
 */
public class AtomModuleGenerator implements ModuleGenerator {

	private final static Namespace ATOM_NS = Namespace.getNamespace("atom", AtomModule.ATOM_10_URI);

	private final static Set<Namespace> namespaces = Collections.singleton(ATOM_NS);

	private void addLinks(final Element parent, final AtomContent content) {
		for (final Link link : content.getLinks()) {
			final Element e = new Element("link", ATOM_NS);

			if (!isBlank(link.getRel())) {
				e.setAttribute(new Attribute("rel", link.getRel()));
			}
			if (!isBlank(link.getType())) {
				e.setAttribute(new Attribute("type", link.getType()));
			}
			if (!isBlank(link.getHref())) {
				e.setAttribute(new Attribute("href", link.getHref()));
			}
			if (!isBlank(link.getHreflang())) {
				e.setAttribute(new Attribute("hreflang", link.getHreflang()));
			}
			if (!isBlank(link.getTitle())) {
				e.setAttribute(new Attribute("title", link.getTitle()));
			}
			if (link.getLength() != 0) {
				e.setAttribute(new Attribute("length", Long.toString(link.getLength())));
			}
			parent.addContent(e);
		}
	}

	@Override
	public void generate(Module module, Element element) {
		final AtomContent content = ((AtomModule) module).getContent();

		addLinks(element, content);
	}

	@Override
	public Set<Namespace> getNamespaces() {
		return namespaces;
	}

	@Override
	public String getNamespaceUri() {
		return AtomModule.ATOM_10_URI;
	}
}