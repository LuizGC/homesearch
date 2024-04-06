import org.openstreetmap.osmosis.core.container.v0_6.*

class AddressProcessor(val addresses: MutableSet<Address>) : EntityProcessor {

    override fun process(p0: BoundContainer?) {
        println("bound")
    }


    override fun process(node: NodeContainer?) {
        extractAddress(node)?.let {
            addresses.add(it)
            //println("${node?.entity?.latitude}, ${node?.entity?.longitude}")
        }
    }

    override fun process(way: WayContainer?) {
        extractAddress(way)?.let {
            addresses.add(it)
        }
    }

    override fun process(relation: RelationContainer?) {
        extractAddress(relation)?.let {
            addresses.add(it)
        }
    }

}