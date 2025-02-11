#!/usr/bin/env bash
feat_dir="./app/src/main/kotlin/feature"
template_name="_Template"
input_name=$1

source_dir="${feat_dir}/${template_name,,}"
dest_dir="${feat_dir}/${input_name,,}"

rm -rf $dest_dir

cp -R $source_dir "$dest_dir/"
cd "$dest_dir"

for file in $(find . -name ${template_name}*.kt); do
    sed -i '' "s/${template_name}/${input_name}/g" $file
    sed -i '' "s/${template_name,,}/${input_name,,}/g" $file
    mv -v "$file" "${file/${template_name}/${input_name}}"
done

echo "======================================"
echo "============ SUCCESSFULLY ============"
echo "======================================"