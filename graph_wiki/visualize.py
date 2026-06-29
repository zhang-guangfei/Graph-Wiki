"""域级可视化：D3.js 力导向图，每个节点为一个业务域"""

import json
from pathlib import Path

from .models import Domain


def export_domain_html(domains: list[Domain], output_path: Path = Path("domain_graph.html")):
    domain_data = []
    for d in domains:
        domain_data.append({
            "name": d.name or d.id,
            "anchors": d.anchors_count(),
            "total_nodes": d.total_files,
            "roles": _count_roles(d),
        })

    links = []
    for d in domains:
        name = d.name or d.id
        for dep in d.dependencies:
            links.append({"source": name, "target": dep.get("domain", ""), "weight": dep.get("import_count", 0)})

    html = _render_html(domain_data, links)
    output_path.write_text(html, encoding="utf-8")


def _count_roles(domain: Domain) -> dict:
    from collections import Counter
    roles = Counter()
    for anchors in domain.anchors.values():
        for a in anchors:
            roles[a.get("_role", "?")] += 1
    return dict(roles)


def _render_html(domains: list[dict], links: list[dict]) -> str:
    djson = json.dumps(domains, ensure_ascii=False)
    ljson = json.dumps(links, ensure_ascii=False)
    return f'''<!DOCTYPE html><html lang="zh"><head><meta charset="utf-8">
<title>业务域依赖图谱</title><style>
*{{margin:0;padding:0;box-sizing:border-box}}
body{{font-family:-apple-system,"Microsoft YaHei",sans-serif;overflow:hidden;background:#f5f5f5}}
svg{{width:100vw;height:100vh}}
.node circle{{stroke:#fff;stroke-width:2.5px;cursor:pointer}}
.node text{{font-size:13px;pointer-events:none;font-weight:500}}
.link{{stroke:#ccc;stroke-opacity:0.5}}
.tooltip{{position:absolute;padding:10px 14px;background:rgba(0,0,0,0.88);color:#fff;border-radius:6px;font-size:13px;pointer-events:none;max-width:300px}}
.header{{position:absolute;top:16px;left:16px;background:white;padding:14px 20px;border-radius:10px;box-shadow:0 2px 16px rgba(0,0,0,0.12);font-size:16px;font-weight:600;z-index:10}}
</style></head><body>
<div class="header">业务域依赖图谱 &mdash; {len(domains)} 个域</div>
<div class="tooltip" style="display:none"></div><svg></svg>
<script>
// CDN 加载 D3.js，失败时回退到备选 CDN
(function() {{
  var s=document.createElement('script');
  s.src='https://d3js.org/d3.v7.min.js';
  s.onerror=function(){{document.write('<script src=\"https://cdn.jsdelivr.net/npm/d3@7\"><\\/script>');}};
  s.onload=function(){{initGraph();}};
  document.head.appendChild(s);
}})();
function initGraph() {{
const domains={djson},links={ljson};
const W=window.innerWidth,H=window.innerHeight;
const colors=d3.scaleOrdinal(d3.schemeTableau10);
const sim=d3.forceSimulation(domains)
  .force("link",d3.forceLink(links).id(d=>d.name).distance(180))
  .force("charge",d3.forceManyBody().strength(-400))
  .force("center",d3.forceCenter(W/2,H/2))
  .force("collision",d3.forceCollide().radius(d=>Math.sqrt(d.anchors)*8+25));
const svg=d3.select("svg").attr("viewBox",[0,0,W,H]);
const g=svg.append("g");
svg.call(d3.zoom().scaleExtent([0.15,5]).on("zoom",e=>g.attr("transform",e.transform)));
svg.append("defs").selectAll("marker").data(["arrow"]).join("marker")
  .attr("id","arrow").attr("viewBox","0 -5 10 10").attr("refX",28).attr("refY",0)
  .attr("markerWidth",6).attr("markerHeight",6).attr("orient","auto")
  .append("path").attr("fill","#aaa").attr("d","M0,-5L10,0L0,5");
g.append("g").selectAll("line").data(links).join("line")
  .attr("stroke","#bbb")
  .attr("stroke-opacity",d=>Math.min(0.6,d.weight/200+0.1))
  .attr("stroke-width",d=>Math.max(0.3,Math.min(10,d.weight/200)))
  .attr("marker-end","url(#arrow)");
const tip=d3.select(".tooltip");
const node=g.append("g").selectAll("g").data(domains).join("g")
  .call(d3.drag()
    .on("start",(e,d)=>{{if(!e.active)sim.alphaTarget(0.3).restart();d.fx=d.x;d.fy=d.y}})
    .on("drag",(e,d)=>{{d.fx=e.x;d.fy=e.y}})
    .on("end",(e,d)=>{{if(!e.active)sim.alphaTarget(0);d.fx=null;d.fy=null}}))
  .on("mouseenter",(e,d)=>{{
    const roles=Object.entries(d.roles).map(([r,c])=>r+":"+c).join(", ");
    tip.style("display","block").html("<b>"+d.name+"</b><br>锚点:"+d.anchors+" | 节点:"+d.total_nodes+"<br>"+roles);
  }})
  .on("mousemove",e=>tip.style("left",(e.pageX+15)+"px").style("top",(e.pageY-30)+"px"))
  .on("mouseleave",()=>tip.style("display","none"));
node.append("circle")
  .attr("r",d=>Math.max(8,Math.sqrt(d.anchors)*6))
  .attr("fill",d=>colors(d.name)).attr("opacity",0.85);
node.append("text").text(d=>d.name)
  .attr("x",d=>Math.sqrt(d.anchors)*6+10).attr("y",5)
  .attr("font-size",d=>Math.min(15,Math.max(9,d.anchors/3+7)));
sim.on("tick",()=>{{
  g.selectAll("line").attr("x1",d=>d.source.x).attr("y1",d=>d.source.y)
      .attr("x2",d=>d.target.x).attr("y2",d=>d.target.y);
  node.attr("transform",d=>`translate(${{d.x}},${{d.y}})`);
}});
}}  // end initGraph
</script></body></html>'''
